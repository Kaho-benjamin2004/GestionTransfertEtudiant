package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.CONTROLLER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.request.ValidationActionRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.SERVICE.IValidationMetier;
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
@RequestMapping("/validations/actions")
@RequiredArgsConstructor
public class ActionValidationController {

    private final IValidationMetier validationMetier;

    private UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getId();
    }

    @GetMapping("/valider/{id}")
    @PreAuthorize("hasAnyRole('AGENT', 'COMMISSION', 'UNIV_A', 'UNIV_B')")
    public String formValider(@PathVariable UUID id, Model model) {
        if (!model.containsAttribute("actionRequest")) {
            model.addAttribute("actionRequest", new ValidationActionRequestDTO());
        }
        model.addAttribute("validationId", id);
        return "validation/valider";
    }

    @PostMapping("/valider/{id}")
    @PreAuthorize("hasAnyRole('AGENT', 'COMMISSION', 'UNIV_A', 'UNIV_B')")
    public String valider(@PathVariable UUID id,
                          @Valid @ModelAttribute("actionRequest") ValidationActionRequestDTO request,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "validation/valider";
        }
        try {
            validationMetier.valider(id, getCurrentUserId(), request.getCommentaire());
            redirectAttributes.addFlashAttribute("success", "Validation acceptée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/validations/attentes";
    }

    @GetMapping("/rejeter/{id}")
    @PreAuthorize("hasAnyRole('AGENT', 'COMMISSION', 'UNIV_A', 'UNIV_B')")
    public String formRejeter(@PathVariable UUID id, Model model) {
        if (!model.containsAttribute("actionRequest")) {
            model.addAttribute("actionRequest", new ValidationActionRequestDTO());
        }
        model.addAttribute("validationId", id);
        return "validation/rejeter";
    }

    @PostMapping("/rejeter/{id}")
    @PreAuthorize("hasAnyRole('AGENT', 'COMMISSION', 'UNIV_A', 'UNIV_B')")
    public String rejeter(@PathVariable UUID id,
                          @Valid @ModelAttribute("actionRequest") ValidationActionRequestDTO request,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "validation/rejeter";
        }
        try {
            validationMetier.rejeter(id, getCurrentUserId(), request.getCommentaire());
            redirectAttributes.addFlashAttribute("success", "Validation rejetée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/validations/attentes";
    }

    @GetMapping("/demander-revision/{id}")
    @PreAuthorize("hasAnyRole('AGENT', 'ETUDIANT')")
    public String formDemanderRevision(@PathVariable UUID id, Model model) {
        if (!model.containsAttribute("actionRequest")) {
            model.addAttribute("actionRequest", new ValidationActionRequestDTO());
        }
        model.addAttribute("validationId", id);
        return "validation/demander-revision";
    }

    @PostMapping("/demander-revision/{id}")
    @PreAuthorize("hasAnyRole('AGENT', 'ETUDIANT')")
    public String demanderRevision(@PathVariable UUID id,
                                   @Valid @ModelAttribute("actionRequest") ValidationActionRequestDTO request,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "validation/demander-revision";
        }
        try {
            validationMetier.demanderRevision(id, getCurrentUserId(), request.getCommentaire());
            redirectAttributes.addFlashAttribute("success", "Révision demandée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/validations/mes-soumissions";
    }
}
