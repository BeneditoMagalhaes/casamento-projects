package com.casamento.wedding.notify;

import com.casamento.wedding.rsvp.RsvpEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Manda um e-mail para os noivos sempre que alguem confirma presenca pelo
 * site. So funciona se as variaveis de ambiente de e-mail (MAIL_USERNAME,
 * MAIL_PASSWORD e NOTIFY_EMAIL) estiverem configuradas — sem elas, o metodo
 * simplesmente nao faz nada, sem quebrar a confirmacao de presenca.
 */
@Service
public class RsvpNotificationService {

    private static final Logger log = LoggerFactory.getLogger(RsvpNotificationService.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final JavaMailSender mailSender;

    @Value("${app.notify-email:}")
    private String notifyEmail;

    @Value("${spring.mail.username:}")
    private String mailFrom;

    public RsvpNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void notifyNewRsvp(RsvpEntry entry) {
        if (notifyEmail == null || notifyEmail.isBlank()) {
            // notificacao por e-mail nao configurada; ignora silenciosamente
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notifyEmail.split(","));
            if (mailFrom != null && !mailFrom.isBlank()) {
                message.setFrom(mailFrom);
            }
            message.setSubject("Nova confirmação de presença: " + entry.getName());
            message.setText(buildBody(entry));
            mailSender.send(message);
        } catch (Exception e) {
            // nunca deixa um problema de e-mail impedir a confirmacao de ser salva
            log.warn("Falha ao enviar e-mail de notificacao de RSVP para {}", entry.getName(), e);
        }
    }

    private String buildBody(RsvpEntry entry) {
        String data = entry.getCreatedAt() != null
                ? DATE_FORMAT.withZone(ZoneId.of("America/Sao_Paulo")).format(entry.getCreatedAt())
                : "";
        StringBuilder body = new StringBuilder();
        body.append("Vocês receberam uma nova confirmação de presença no site do casamento!\n\n");
        body.append("Nome: ").append(entry.getName()).append("\n");
        body.append("Acompanhantes: ").append(nullToDash(entry.getGuests())).append("\n");
        body.append("Presença: ").append(nullToDash(entry.getAnswer())).append("\n");
        if (entry.getMessage() != null && !entry.getMessage().isBlank()) {
            body.append("Recado: ").append(entry.getMessage()).append("\n");
        }
        body.append("Data da confirmação: ").append(data).append("\n\n");
        body.append("Para ver todas as confirmações, entre no painel admin do site.");
        return body.toString();
    }

    private String nullToDash(String value) {
        return (value == null || value.isBlank()) ? "-" : value;
    }
}
