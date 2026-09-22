package com.casamento.wedding.gifts;

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
@RequestMapping("/api/gifts")
public class GiftController {

    private static final List<String> ALLOWED_IMAGE_TYPES = List.of("image/jpeg", "image/png", "image/webp", "image/gif");

    private final GiftRepository repository;
    private final AuthService authService;

    @Value("${app.upload-dir}")
    private String uploadDir;

    public GiftController(GiftRepository repository, AuthService authService) {
        this.repository = repository;
        this.authService = authService;
    }

    /** Publico: lista de presentes visivel para os convidados. */
    @GetMapping
    public List<GiftItem> list() {
        return repository.findAll();
    }

    /** Somente admin logado. */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody GiftItem gift, HttpServletRequest request) {
        if (!authService.isAdmin(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Nao autenticado"));
        }
        if (gift.getTitle() == null || gift.getTitle().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Titulo e obrigatorio"));
        }
        if (!isValidLinkOrEmpty(gift.getPixLink()) || !isValidLinkOrEmpty(gift.getCardLink())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Os links de pagamento precisam comecar com https://"));
        }
        gift.setId(null);
        return ResponseEntity.ok(repository.save(gift));
    }

    /** Somente admin logado: envia/atualiza a foto de um presente ja existente. */
    @PostMapping("/{id}/image")
    public ResponseEntity<?> uploadImage(@PathVariable("id") Long id, @RequestParam("image") MultipartFile image, HttpServletRequest request) throws IOException {
        if (!authService.isAdmin(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Nao autenticado"));
        }
        var existing = repository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Arquivo vazio"));
        }
        String contentType = image.getContentType();
        if (contentType == null || !ALLOWED_IMAGE_TYPES.contains(contentType)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Formato de imagem nao suportado (use JPG, PNG, WEBP ou GIF)"));
        }

        Path dir = Paths.get(uploadDir, "gifts");
        Files.createDirectories(dir);

        String ext = switch (contentType) {
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            case "image/gif" -> ".gif";
            default -> ".jpg";
        };
        String filename = UUID.randomUUID() + ext;
        image.transferTo(dir.resolve(filename));

        GiftItem gift = existing.get();
        gift.setImageUrl("/uploads/gifts/" + filename);
        return ResponseEntity.ok(repository.save(gift));
    }

    /** Somente admin logado. */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody GiftItem gift, HttpServletRequest request) {
        if (!authService.isAdmin(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Nao autenticado"));
        }
        if (!isValidLinkOrEmpty(gift.getPixLink()) || !isValidLinkOrEmpty(gift.getCardLink())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Os links de pagamento precisam comecar com https://"));
        }
        return repository.findById(id).map(existing -> {
            existing.setTitle(gift.getTitle());
            existing.setDescription(gift.getDescription());
            existing.setPrice(gift.getPrice());
            existing.setIcon(gift.getIcon());
            existing.setImageUrl(gift.getImageUrl());
            existing.setPixLink(gift.getPixLink());
            existing.setCardLink(gift.getCardLink());
            return ResponseEntity.ok(repository.save(existing));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /** Aceita vazio/nulo (forma de pagamento nao configurada) ou um link https. */
    private boolean isValidLinkOrEmpty(String link) {
        return link == null || link.isBlank() || link.startsWith("https://");
    }

    /** Somente admin logado. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id, HttpServletRequest request) {
        if (!authService.isAdmin(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Nao autenticado"));
        }
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.ok(Map.of("deleted", true));
    }
}
