package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.CONTROLLEUR;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.RegistrationDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.ChangementMotDePasseRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.LoginRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.PasswordResetToken;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Profil;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Utilisateur;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.PasswordResetTokenRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.ProfilRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.UtilisateurRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.AuthService;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.EmailService;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.UtilisateurService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UtilisateurService utilisateurService;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;
    private final ProfilRepository profilRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    // ========== CONNEXION ==========
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        if (!model.containsAttribute("loginRequest")) {
            model.addAttribute("loginRequest", new LoginRequestDTO());
        }
        return "auth/login";
    }

    // Le traitement du login est géré par Spring Security (loginProcessingUrl), pas besoin de méthode POST ici.
    // Cependant, si vous voulez personnaliser, vous pouvez ajouter un POST /login, mais ce n'est pas obligatoire.

    // ========== INSCRIPTION ==========
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        if (!model.containsAttribute("registration")) {
            model.addAttribute("registration", new RegistrationDTO());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("registration") RegistrationDTO registration,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        try {
            utilisateurService.createUser(registration.getUtilisateurRequest());
            redirectAttributes.addFlashAttribute("success", "Inscription réussie. Veuillez vous connecter.");
            return "redirect:/auth/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/auth/register";
        }
    }

    // ========== CHANGEMENT DE MOT DE PASSE (connecté) ==========
    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        if (!model.containsAttribute("changementRequest")) {
            model.addAttribute("changementRequest", new ChangementMotDePasseRequestDTO());
        }
        return "auth/change-password";
    }

    @PostMapping("/change-password")
    public String processChangePassword(@Valid @ModelAttribute("changementRequest") ChangementMotDePasseRequestDTO request,
                                        BindingResult result,
                                        HttpSession session,
                                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "auth/change-password";
        }
        UUID userId = (UUID) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/auth/login";
        }
        try {
            authService.changePassword(userId, request);
            session.invalidate();
            redirectAttributes.addFlashAttribute("success", "Mot de passe changé, veuillez vous reconnecter.");
            return "redirect:/auth/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/auth/change-password";
        }
    }

    // ========== MOT DE PASSE OUBLIÉ ==========
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email,
                                        RedirectAttributes redirectAttributes) {
        Optional<Profil> profilOpt = profilRepository.findByEmail(email);
        if (profilOpt.isPresent()) {
            Utilisateur utilisateur = profilOpt.get().getUtilisateur();
            String token = UUID.randomUUID().toString();
            LocalDateTime expiration = LocalDateTime.now().plusHours(1);
            PasswordResetToken resetToken = PasswordResetToken.builder()
                    .token(token)
                    .utilisateur(utilisateur)
                    .expirationDate(expiration)
                    .used(false)
                    .build();
            tokenRepository.save(resetToken);

            String resetUrl = "http://localhost:8080/auth/reset-password?token=" + token;
            String message = "<p>Bonjour,</p>"
                    + "<p>Vous avez demandé la réinitialisation de votre mot de passe.</p>"
                    + "<p>Cliquez sur le lien suivant pour le modifier :</p>"
                    + "<a href=\"" + resetUrl + "\">" + resetUrl + "</a>"
                    + "<p>Ce lien est valable 1 heure.</p>";
            emailService.sendEmail(email, "Réinitialisation de votre mot de passe", message);
        }
        redirectAttributes.addFlashAttribute("success",
                "Si l'adresse email existe, vous allez recevoir un lien de réinitialisation.");
        return "redirect:/auth/forgot-password";
    }

    // ========== RÉINITIALISATION DU MOT DE PASSE (avec token) ==========
    @GetMapping("/reset-password")
    public String showResetForm(@RequestParam("token") String token,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token).orElse(null);
        if (resetToken == null || resetToken.isUsed() || resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("error", "Lien invalide ou expiré.");
            return "redirect:/auth/forgot-password";
        }
        model.addAttribute("token", token);
        return "auth/reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword,
                                RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Les mots de passe ne correspondent pas.");
            return "redirect:/auth/reset-password?token=" + token;
        }

        PasswordResetToken resetToken = tokenRepository.findByToken(token).orElse(null);
        if (resetToken == null || resetToken.isUsed() || resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("error", "Lien invalide ou expiré.");
            return "redirect:/auth/forgot-password";
        }

        Utilisateur utilisateur = resetToken.getUtilisateur();
        utilisateur.setMotDePasseHash(passwordEncoder.encode(password));
        utilisateurRepository.save(utilisateur);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);

        redirectAttributes.addFlashAttribute("success", "Mot de passe modifié avec succès. Veuillez vous connecter.");
        return "redirect:/auth/login";
    }
}