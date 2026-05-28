package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.CONTROLLEUR;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.ProfilRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.UtilisateurDetailResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final UtilisateurService utilisateurService;

    @GetMapping
    public String viewProfile(HttpSession session, Model model) {
        UUID userId = (UUID) session.getAttribute("userId");
        if (userId == null) return "redirect:/auth/login";
        UtilisateurDetailResponseDTO user = utilisateurService.getUserById(userId);
        model.addAttribute("user", user);
        return "profile/view";
    }

    @GetMapping("/edit")
    public String showEditProfileForm(HttpSession session, Model model) {
        UUID userId = (UUID) session.getAttribute("userId");
        if (userId == null) return "redirect:/auth/login";
        UtilisateurDetailResponseDTO user = utilisateurService.getUserById(userId);
        if (!model.containsAttribute("profilRequest")) {
            ProfilRequestDTO request = new ProfilRequestDTO();
            request.setNom(user.getProfil().getNom());
            request.setPrenom(user.getProfil().getPrenom());
            request.setEmail(user.getProfil().getEmail());
            request.setTelephone(user.getProfil().getTelephone());
            request.setMatriculeNational(user.getProfil().getMatriculeNational());
//            request.setFonction(user.getProfil().getFonction());
            model.addAttribute("profilRequest", request);
        }
        return "profile/edit";
    }

    @PostMapping("/edit")
    public String processEditProfile(@Valid @ModelAttribute("profilRequest") ProfilRequestDTO request,
                                     BindingResult result,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "profile/edit";
        }
        UUID userId = (UUID) session.getAttribute("userId");
        try {
            utilisateurService.updateProfil(userId, request);
            redirectAttributes.addFlashAttribute("success", "Profil mis à jour.");
            return "redirect:/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/profile/edit";
        }
    }
}