package com.casamento.wedding.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public record LoginRequest(String username, String password) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest body, HttpServletRequest request) {
        if (body == null || !constantTimeEquals(body.username(), adminUsername)
                || !constantTimeEquals(body.password(), adminPassword)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario ou senha invalidos"));
        }
        // regenera a sessao no login para evitar fixation de sessao
        request.getSession().invalidate();
        request.getSession(true).setAttribute("isAdmin", Boolean.TRUE);
        return ResponseEntity.ok(Map.of("authenticated", true));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        var session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(Map.of("authenticated", false));
    }

    @GetMapping("/status")
    public ResponseEntity<?> status(HttpServletRequest request) {
        return ResponseEntity.ok(Map.of("authenticated", authService.isAdmin(request)));
    }

    // DIAGNOSTICO TEMPORARIO — nao expoe a senha, so confirma se as variaveis
    // de ambiente estao chegando no servidor. Remover depois de usar.
    @GetMapping("/debug-config")
    public ResponseEntity<?> debugConfig() {
        boolean usernameIsDefault = "noivos".equals(adminUsername);
        boolean passwordIsDefault = "trocar123".equals(adminPassword);
        return ResponseEntity.ok(Map.of(
                "usernameIsDefault", usernameIsDefault,
                "passwordIsDefault", passwordIsDefault,
                "usernameLength", adminUsername == null ? -1 : adminUsername.length(),
                "passwordLength", adminPassword == null ? -1 : adminPassword.length(),
                "envAdminUsernameRaw", System.getenv("ADMIN_USERNAME") == null ? "AUSENTE" : "presente, tamanho=" + System.getenv("ADMIN_USERNAME").length(),
                "envAdminPasswordRaw", System.getenv("ADMIN_PASSWORD") == null ? "AUSENTE" : "presente, tamanho=" + System.getenv("ADMIN_PASSWORD").length()
        ));
    }

    private boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) return false;
        return MessageDigest.isEqual(a.getBytes(StandardCharsets.UTF_8), b.getBytes(StandardCharsets.UTF_8));
    }
}
