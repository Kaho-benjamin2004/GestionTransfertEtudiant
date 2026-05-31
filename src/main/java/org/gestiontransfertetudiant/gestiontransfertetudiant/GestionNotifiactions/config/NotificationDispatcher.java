package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.config;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.Repository.PreferenceNotificationRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.request.NotificationRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.entity.PreferenceNotification;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.UtilisateurService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationDispatcher {

    private final SimpMessagingTemplate webSocketTemplate;
    private final JavaMailSender mailSender;
    private final PreferenceNotificationRepository preferenceRepository;

    /**
     * Envoie une notification via les canaux configurés pour l'utilisateur.
     */
    @Async
    public void envoyer(NotificationRequestDTO request) {
        UUID userId = request.getDestinataireId();
        List<PreferenceNotification> prefs = preferenceRepository.findByUtilisateurId(userId);
        boolean emailEnabled = prefs.stream().anyMatch(p -> "EMAIL".equals(p.getCanal()) && p.getActif());
        boolean inAppEnabled = prefs.stream().anyMatch(p -> "IN_APP".equals(p.getCanal()) && p.getActif());

        if (emailEnabled) {
            envoyerEmail(userId, request);
        }
        if (inAppEnabled) {
            envoyerInApp(userId, request);
        }
        // SMS non implémenté ici
    }



    private void envoyerEmail(UUID userId, NotificationRequestDTO request) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(getUserEmail(userId)); // méthode à implémenter pour récupérer l'email via le Module 1
            helper.setSubject(request.getTitre());
            helper.setText(request.getMessage(), true); // true = HTML
            mailSender.send(message);
            log.info("Email envoyé à l'utilisateur {}", userId);
        } catch (MessagingException e) {
            log.error("Erreur lors de l'envoi d'email à {}", userId, e);
        }
    }

    private void envoyerInApp(UUID userId, NotificationRequestDTO request) {
        // Convertir la notification en DTO pour l'envoyer via WebSocket
        String destination = "/topic/notifications/" + userId;
        webSocketTemplate.convertAndSend(destination, request);
        log.info("Notification WebSocket envoyée à {}", destination);
    }

    private String getUserEmail(UUID userId) {
        // Appeler un service du Module 1 pour récupérer l'email du profil
        // Par exemple : utilisateurService.getEmailById(userId);
        return "utilisateur@example.com"; // temporaire
    }
}