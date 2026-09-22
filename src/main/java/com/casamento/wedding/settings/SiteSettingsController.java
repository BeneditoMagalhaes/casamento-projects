package com.casamento.wedding.settings;

import com.casamento.wedding.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/settings")
public class SiteSettingsController {

    private static final Long SETTINGS_ID = 1L;
    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png", "image/webp", "image/gif");

    private final SiteSettingsRepository repository;
    private final AuthService authService;

    @Value("${app.upload-dir}")
    private String uploadDir;

    public SiteSettingsController(SiteSettingsRepository repository, AuthService authService) {
        this.repository = repository;
        this.authService = authService;
    }

    /** Publico: usado pela home para saber qual foto de capa exibir. */
    @GetMapping
    public Map<String, Object> get() {
        String coverPhotoUrl = repository.findById(SETTINGS_ID)
                .map(SiteSettings::getCoverPhotoUrl)
                .orElse(null);
        Map<String, Object> body = new java.util.HashMap<>();
        body.put("coverPhotoUrl", coverPhotoUrl);
        return body;
    }

    /** Somente admin logado: envia/troca a foto de capa da home. */
    @PostMapping("/cover-photo")
    public ResponseEntity<?> uploadCoverPhoto(@RequestParam("photo") MultipartFile photo, HttpServletRequest request) throws IOException {
        if (!authService.isAdmin(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Nao autenticado"));
        }
        if (photo.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Arquivo vazio"));
        }
        String contentType = photo.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Formato de imagem nao suportado (use JPG, PNG, WEBP ou GIF)"));
        }

        Path dir = Paths.get(uploadDir, "site");
        Files.createDirectories(dir);

        String ext = switch (contentType) {
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            case "image/gif" -> ".gif";
            default -> ".jpg";
        };
        String filename = UUID.randomUUID() + ext;
        photo.transferTo(dir.resolve(filename));

        SiteSettings settings = repository.findById(SETTINGS_ID).orElseGet(SiteSettings::new);
        String oldFilename = extractFilename(settings.getCoverPhotoUrl());
        settings.setCoverPhotoUrl("/uploads/site/" + filename);
        repository.save(settings);

        if (oldFilename != null) {
            Files.deleteIfExists(dir.resolve(oldFilename));
        }

        return ResponseEntity.ok(Map.of("coverPhotoUrl", settings.getCoverPhotoUrl()));
    }

    /** Somente admin logado: remove a foto de capa (volta ao fundo padrao). */
    @DeleteMapping("/cover-photo")
    public ResponseEntity<?> deleteCoverPhoto(HttpServletRequest request) throws IOException {
        if (!authService.isAdmin(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Nao autenticado"));
        }
        var existing = repository.findById(SETTINGS_ID);
        if (existing.isPresent()) {
            String oldFilename = extractFilename(existing.get().getCoverPhotoUrl());
            if (oldFilename != null) {
                Files.deleteIfExists(Paths.get(uploadDir, "site", oldFilename));
            }
            existing.get().setCoverPhotoUrl(null);
            repository.save(existing.get());
        }
        Map<String, Object> body = new java.util.HashMap<>();
        body.put("coverPhotoUrl", null);
        return ResponseEntity.ok(body);
    }

    private String extractFilename(String coverPhotoUrl) {
        if (coverPhotoUrl == null || coverPhotoUrl.isBlank()) return null;
        int idx = coverPhotoUrl.lastIndexOf('/');
        return idx >= 0 ? coverPhotoUrl.substring(idx + 1) : coverPhotoUrl;
    }
}
