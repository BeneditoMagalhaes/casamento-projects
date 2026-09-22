package com.casamento.wedding.rsvp;

import com.casamento.wedding.auth.AuthService;
import com.casamento.wedding.notify.RsvpNotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rsvp")
public class RsvpController {

    private final RsvpRepository repository;
    private final AuthService authService;
    private final RsvpNotificationService notificationService;

    public RsvpController(RsvpRepository repository, AuthService authService, RsvpNotificationService notificationService) {
        this.repository = repository;
        this.authService = authService;
        this.notificationService = notificationService;
    }

    /** Publico: qualquer convidado pode confirmar presenca. */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody RsvpEntry entry) {
        if (entry.getName() == null || entry.getName().isBlank()
                || entry.getAnswer() == null || entry.getAnswer().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Nome e presenca sao obrigatorios"));
        }
        entry.setId(null);
        RsvpEntry saved = repository.save(entry);
        notificationService.notifyNewRsvp(saved);
        return ResponseEntity.ok(Map.of("saved", true));
    }

    /** Somente admin logado: ve a lista de confirmacoes. */
    @GetMapping
    public ResponseEntity<?> list(HttpServletRequest request) {
        if (!authService.isAdmin(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Nao autenticado"));
        }
        return ResponseEntity.ok(repository.findAllByOrderByCreatedAtDesc());
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
