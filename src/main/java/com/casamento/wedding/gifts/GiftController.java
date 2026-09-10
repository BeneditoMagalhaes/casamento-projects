package com.casamento.wedding.gifts;

import com.casamento.wedding.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gifts")
public class GiftController {

    private final GiftRepository repository;
    private final AuthService authService;

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

    /** Somente admin logado. */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody GiftItem gift, HttpServletRequest request) {
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
    public ResponseEntity<?> delete(@PathVariable Long id, HttpServletRequest request) {
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
