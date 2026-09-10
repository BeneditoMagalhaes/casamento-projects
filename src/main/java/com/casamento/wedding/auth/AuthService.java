package com.casamento.wedding.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    /** Verifica, a partir da sessao do navegador, se quem esta chamando esta logado como admin. */
    public boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && Boolean.TRUE.equals(session.getAttribute("isAdmin"));
    }
}
