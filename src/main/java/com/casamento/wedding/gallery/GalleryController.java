package com.casamento.wedding.gallery;

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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gallery")
public class GalleryController {

    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png", "image/webp", "image/gif");

    private final GalleryRepository repository;
    private final AuthService authService;

    @Value("${app.upload-dir}")
    private String uploadDir;

    public GalleryController(GalleryRepository repository, AuthService authService) {
        this.repository = repository;
        this.authService = authService;
    }

    /** Publico: qualquer visitante do site ve as fotos. */
    @GetMapping
    public List<Map<String, Object>> list() {
        return repository.findAllByOrderByIdAsc().stream()
                .map(p -> Map.<String, Object>of(
                        "id", p.getId(),
                        "url", "/uploads/gallery/" + p.getFilename()
                ))
                .collect(Collectors.toList());
    }

    /** Somente admin logado: envia uma nova foto. */
    @PostMapping
    public ResponseEntity<?> upload(@RequestParam("photo") MultipartFile photo, HttpServletRequest request) throws IOException {
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

        Path dir = Paths.get(uploadDir, "gallery");
        Files.createDirectories(dir);

        String ext = switch (contentType) {
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            case "image/gif" -> ".gif";
            default -> ".jpg";
        };
        String filename = UUID.randomUUID() + ext;
        photo.transferTo(dir.resolve(filename));

        GalleryPhoto saved = repository.save(new GalleryPhoto(filename));
        return ResponseEntity.ok(Map.of("id", saved.getId(), "url", "/uploads/gallery/" + filename));
    }

    /** Somente admin logado: remove uma foto. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) throws IOException {
        if (!authService.isAdmin(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Nao autenticado"));
        }
        var photo = repository.findById(id);
        if (photo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Files.deleteIfExists(Paths.get(uploadDir, "gallery", photo.get().getFilename()));
        repository.deleteById(id);
        return ResponseEntity.ok(Map.of("deleted", true));
    }
}
