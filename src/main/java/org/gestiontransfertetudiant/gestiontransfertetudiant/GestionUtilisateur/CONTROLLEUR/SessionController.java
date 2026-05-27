package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.CONTROLLEUR;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.SessionResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    @GetMapping
    public String listUserSessions(HttpSession httpSession, Model model) {
        UUID userId = (UUID) httpSession.getAttribute("userId");
        if (userId == null) return "redirect:/auth/login";
        List<SessionResponseDTO> sessions = sessionService.getSessionsByUser(userId);
        model.addAttribute("sessions", sessions);
        return "sessions/list";
    }
}