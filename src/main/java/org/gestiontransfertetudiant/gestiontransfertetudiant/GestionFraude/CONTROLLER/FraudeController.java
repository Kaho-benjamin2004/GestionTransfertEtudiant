package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.CONTROLLER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto.DetectionResultDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto.RegleDetectionRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.SERVICE.IFraudeMetier;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.UUID;

@Controller
@RequestMapping("/admin/fraude")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class FraudeController {

    private final IFraudeMetier fraudeMetier;

    private UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getId();
    }

    // ========== Règles de détection ==========
    @GetMapping("/regles")
    public String listerRegles(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               Model model) {
        model.addAttribute("regles", fraudeMetier.listerRegles(PageRequest.of(page, size)));
        return "admin/fraude/regles/liste";
    }

    @GetMapping("/regles/creer")
    public String formCreerRegle(Model model) {
        if (!model.containsAttribute("regleRequest")) {
            model.addAttribute("regleRequest", new RegleDetectionRequestDTO());
        }
        return "admin/fraude/regles/creer";
    }

    @PostMapping("/regles/creer")
    public String creerRegle(@Valid @ModelAttribute("regleRequest") RegleDetectionRequestDTO request,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/fraude/regles/creer";
        }
        try {
            fraudeMetier.creerRegle(request);
            redirectAttributes.addFlashAttribute("success", "Règle créée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/fraude/regles";
    }

    @GetMapping("/regles/modifier/{id}")
    public String formModifierRegle(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("regle", fraudeMetier.getRegle(id));
            if (!model.containsAttribute("regleRequest")) {
                RegleDetectionRequestDTO request = new RegleDetectionRequestDTO();
                model.addAttribute("regleRequest", request);
            }
            return "admin/fraude/regles/modifier";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/fraude/regles";
        }
    }

    @PostMapping("/regles/modifier/{id}")
    public String modifierRegle(@PathVariable UUID id,
                                @Valid @ModelAttribute("regleRequest") RegleDetectionRequestDTO request,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/fraude/regles/modifier";
        }
        try {
            fraudeMetier.modifierRegle(id, request);
            redirectAttributes.addFlashAttribute("success", "Règle modifiée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/fraude/regles";
    }

    @PostMapping("/regles/supprimer/{id}")
    public String supprimerRegle(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            fraudeMetier.supprimerRegle(id);
            redirectAttributes.addFlashAttribute("success", "Règle supprimée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/fraude/regles";
    }

    // ========== Détection ==========
    @GetMapping("/verifier")
    public String formVerification(Model model) {
        return "admin/fraude/verification";
    }

    @PostMapping("/verifier")
    public String declencherVerification(@RequestParam String entiteType,
                                         @RequestParam UUID entiteId,
                                         RedirectAttributes redirectAttributes) {
        try {
            DetectionResultDTO result = fraudeMetier.declencherVerificationManuelle(entiteType, entiteId);
            redirectAttributes.addFlashAttribute("resultat", result);
            redirectAttributes.addFlashAttribute("success", "Vérification terminée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/fraude/verifier";
    }

    // ========== Anomalies ==========
    @GetMapping("/anomalies")
    public String listerAnomalies(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {
        model.addAttribute("anomalies", fraudeMetier.listerAnomalies(PageRequest.of(page, size)));
        return "admin/fraude/anomalies/liste";
    }

    @GetMapping("/anomalies/{id}")
    public String voirAnomalie(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("anomalie", fraudeMetier.getAnomalie(id));
            return "admin/fraude/anomalies/voir";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/fraude/anomalies";
        }
    }

    @PostMapping("/anomalies/{id}/resoudre")
    public String resoudreAnomalie(@PathVariable UUID id,
                                   @RequestParam String commentaire,
                                   RedirectAttributes redirectAttributes) {
        try {
            fraudeMetier.resoudreAnomalie(id, commentaire);
            redirectAttributes.addFlashAttribute("success", "Anomalie résolue.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/fraude/anomalies";
    }

    // ========== Journal ==========
    @GetMapping("/journal")
    public String consulterJournal(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "20") int size,
                                   Model model) {
        model.addAttribute("journal", fraudeMetier.consulterJournal(PageRequest.of(page, size)));
        return "admin/fraude/journal/liste";
    }

    // ========== Statistiques ==========
    @GetMapping("/statistiques")
    public String afficherStatistiques(Model model) {
        model.addAttribute("stats", fraudeMetier.getStatistiques());
        return "admin/fraude/statistiques";
    }
}