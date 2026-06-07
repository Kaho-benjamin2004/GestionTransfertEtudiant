//package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE;
//
//import jakarta.mail.internet.MimeMessage;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.PasswordResetToken;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Utilisateur;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.PasswordResetTokenRepository;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.UtilisateurRepository;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.messaging.MessagingException;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.UUID;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class PasswordResetService {
//
//    private final PasswordResetTokenRepository tokenRepository;
//    private final UtilisateurRepository utilisateurRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JavaMailSender mailSender;
//
//    /**
//     * Crée un token de réinitialisation et l'envoie par email à l'utilisateur (asynchrone).
//     * Ne révèle pas si l'email existe ou non (message générique).
//     */
//    @Async
//    public void createAndSendToken(String email) {
//        Optional<Utilisateur> userOpt = utilisateurRepository.findByEmail(email);
//        if (userOpt.isEmpty()) {
//            log.info("Demande de réinitialisation pour email inexistant : {}", email);
//            return; // Ne pas révéler l'existence
//        }
//
//        Utilisateur utilisateur = userOpt.get();
//        String token = UUID.randomUUID().toString();
//        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);
//
//        // Supprimer les anciens tokens non utilisés pour cet utilisateur (optionnel)
//        tokenRepository.deleteByUtilisateur(utilisateur);
//
//        PasswordResetToken resetToken = PasswordResetToken.builder()
//                .token(token)
//                .utilisateur(utilisateur)
//                .expirationDate(expiryDate)
//                .used(false)
//                .build();
//        tokenRepository.save(resetToken);
//
//        String resetUrl = "http://localhost:8080/auth/reset-password?token=" + token;
//        String subject = "Réinitialisation de votre mot de passe";
//        String htmlContent = "<p>Bonjour,</p>"
//                + "<p>Vous avez demandé la réinitialisation de votre mot de passe.</p>"
//                + "<p>Cliquez sur le lien suivant :</p>"
//                + "<a href=\"" + resetUrl + "\">" + resetUrl + "</a>"
//                + "<p>Ce lien est valable 1 heure.</p>";
//
//        sendEmail(email, subject, htmlContent);
//        log.info("Token de réinitialisation créé pour l'email : {}", email);
//    }
//
//    /**
//     * Envoie un email de manière asynchrone.
//     */
//    @Async
//    public void sendEmail(String to, String subject, String htmlText) {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(htmlText, true);
//            mailSender.send(message);
//            log.info("Email envoyé à {}", to);
//        } catch (MessagingException e) {
//            log.error("Erreur lors de l'envoi de l'email à {} : {}", to, e.getMessage());
//        } catch (jakarta.mail.MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * Valide un token de réinitialisation.
//     * @return true si valide et non expiré
//     */
//    public boolean validateToken(String token) {
//        return tokenRepository.findByToken(token)
//                .map(resetToken -> !resetToken.isUsed() && resetToken.getExpirationDate().isAfter(LocalDateTime.now()))
//                .orElse(false);
//    }
//
//    /**
//     * Réinitialise le mot de passe après validation du token.
//     * @throws RuntimeException si token invalide, expiré ou déjà utilisé
//     */
//    @Transactional
//    public void resetPassword(String token, String newPassword) {
//        PasswordResetToken resetToken = tokenRepository.findByToken(token)
//                .orElseThrow(() -> new RuntimeException("Token invalide"));
//
//        if (resetToken.isUsed()) {
//            throw new RuntimeException("Ce token a déjà été utilisé");
//        }
//        if (resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
//            throw new RuntimeException("Token expiré");
//        }
//
//        Utilisateur utilisateur = resetToken.getUtilisateur();
//        utilisateur.setMotDePasseHash(passwordEncoder.encode(newPassword));
//        utilisateurRepository.save(utilisateur);
//
//        resetToken.setUsed(true);
//        tokenRepository.save(resetToken);
//        log.info("Mot de passe réinitialisé pour l'utilisateur : {}", utilisateur.getLogin());
//    }
//}