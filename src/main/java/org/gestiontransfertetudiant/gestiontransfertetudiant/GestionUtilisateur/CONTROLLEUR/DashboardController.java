package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.CONTROLLEUR;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.UtilisateurDetailResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final UtilisateurService utilisateurService;

    @GetMapping
    public String dashboard(HttpSession session, Model model) {
        UUID userId = (UUID) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/auth/login";
        }

        UtilisateurDetailResponseDTO user = utilisateurService.getUserById(userId);
        model.addAttribute("user", user);
        List<String> roles = (List<String>) session.getAttribute("userRoles");

        if (roles.contains("ROLE_ADMIN") || roles.contains("ADMIN")) {
            return "auth/dashboard/admin";
        } else if (roles.contains("ROLE_AGENT") || roles.contains("AGENT")) {
            return "dashboard/agent";
        } else if (roles.contains("ROLE_COMMISSION") || roles.contains("COMMISSION")) {
            return "dashboard/commission";
        } else if (roles.contains("ROLE_UNIV_A") || roles.contains("UNIV_A") || roles.contains("ROLE_UNIV_B") || roles.contains("UNIV_B")) {
            return "dashboard/university";
        } else {
            return "dashboard/student";
        }
    }
}