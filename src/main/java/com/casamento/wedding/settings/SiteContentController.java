package com.casamento.wedding.settings;

import com.casamento.wedding.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/content")
public class SiteContentController {

    private static final Long CONTENT_ID = 1L;

    private final SiteContentRepository repository;
    private final AuthService authService;

    public SiteContentController(SiteContentRepository repository, AuthService authService) {
        this.repository = repository;
        this.authService = authService;
    }

    /** Publico: a home busca aqui os textos/data personalizados pelos noivos. */
    @GetMapping
    public Map<String, String> get() {
        return repository.findById(CONTENT_ID)
                .map(SiteContent::getEntries)
                .orElseGet(HashMap::new);
    }

    /** Somente admin logado: salva os textos/data editados no painel. */
    @PutMapping
    public ResponseEntity<?> save(@RequestBody Map<String, String> body, HttpServletRequest request) {
        if (!authService.isAdmin(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Nao autenticado"));
        }
        SiteContent content = repository.findById(CONTENT_ID).orElseGet(SiteContent::new);
        Map<String, String> entries = new HashMap<>();
        if (body != null) {
            body.forEach((k, v) -> {
                if (k != null && v != null) entries.put(k, v);
            });
        }
        content.setEntries(entries);
        repository.save(content);
        return ResponseEntity.ok(entries);
    }
}
