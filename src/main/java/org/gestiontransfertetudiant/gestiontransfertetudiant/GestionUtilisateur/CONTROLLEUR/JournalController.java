package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.CONTROLLEUR;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.JournalConnexionResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.JournalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/journaux")
public class JournalController {

    private final JournalService journalService;

    @GetMapping
    public String listJournaux(@RequestParam(required = false) UUID userId,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size,
                               Model model) {
        Page<JournalConnexionResponseDTO> journaux;
        if (userId != null) {
            journaux = journalService.getJournauxByUtilisateur(userId, PageRequest.of(page, size));
            model.addAttribute("userId", userId);
        } else {
            journaux = journalService.getAllJournaux(PageRequest.of(page, size));
        }
        model.addAttribute("journaux", journaux);
        return "admin/journaux/list";
    }
}