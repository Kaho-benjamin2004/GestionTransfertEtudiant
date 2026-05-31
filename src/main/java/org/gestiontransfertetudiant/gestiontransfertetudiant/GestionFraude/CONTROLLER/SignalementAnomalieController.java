package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.CONTROLLER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto.AnomalieRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.SERVICE.IFraudeMetier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/fraude/signalement")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('AGENT', 'ADMIN')")
public class SignalementAnomalieController {

    private final IFraudeMetier fraudeMetier;

    @GetMapping
    public String formSignalement(Model model) {
        if (!model.containsAttribute("anomalieRequest")) {
            model.addAttribute("anomalieRequest", new AnomalieRequestDTO());
        }
        return "fraude/signalement";
    }

    @PostMapping
    public String signalerAnomalie(@Valid @ModelAttribute("anomalieRequest") AnomalieRequestDTO request,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "fraude/signalement";
        }
        try {
            fraudeMetier.signalerAnomalie(request);
            redirectAttributes.addFlashAttribute("success", "Anomalie signalée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/fraude/signalement";
    }
}