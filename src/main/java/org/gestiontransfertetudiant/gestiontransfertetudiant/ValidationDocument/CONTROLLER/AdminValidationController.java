package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.CONTROLLER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.request.WorkflowRequestDTO;
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
@RequestMapping("/admin/validations")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminValidationController {

    private final IValidationMetier validationMetier;

    private UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getId();
    }

    @GetMapping("/workflow/creer")
    public String formCreerWorkflow(Model model) {
        if (!model.containsAttribute("workflowRequest")) {
            model.addAttribute("workflowRequest", new WorkflowRequestDTO());
        }
        return "admin/validations/workflow-creer";
    }

    @PostMapping("/workflow/creer")
    public String creerWorkflow(@Valid @ModelAttribute("workflowRequest") WorkflowRequestDTO request,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/validations/workflow-creer";
        }
        try {
            validationMetier.definirWorkflow(request, getCurrentUserId());
            redirectAttributes.addFlashAttribute("success", "Workflow créé avec succès.");
            return "redirect:/admin/validations/workflows";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/validations/workflow-creer";
        }
    }

    @PostMapping("/annuler/{id}")
    public String annulerValidation(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            validationMetier.annulerValidation(id, getCurrentUserId());
            redirectAttributes.addFlashAttribute("success", "Validation annulée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/validations/liste";
    }

    @PostMapping("/attribuer-validateur/{id}")
    public String attribuerValidateur(@PathVariable UUID id,
                                      @RequestParam UUID valideurId,
                                      RedirectAttributes redirectAttributes) {
        try {
            validationMetier.attribuerValidateur(id, valideurId, getCurrentUserId());
            redirectAttributes.addFlashAttribute("success", "Validateur attribué.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/validations/liste";
    }

    @PostMapping("/suspendre/{id}")
    public String suspendreValidation(@PathVariable UUID id,
                                      @RequestParam String raison,
                                      RedirectAttributes redirectAttributes) {
        try {
            validationMetier.suspendreValidation(id, getCurrentUserId(), raison);
            redirectAttributes.addFlashAttribute("success", "Validation suspendue.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/validations/liste";
    }

    @PostMapping("/reprendre/{id}")
    public String reprendreValidation(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            validationMetier.reprendreValidation(id, getCurrentUserId());
            redirectAttributes.addFlashAttribute("success", "Validation reprise.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/validations/liste";
    }

    @GetMapping("/liste")
    public String listerValidations(Model model) {
        // À implémenter avec pagination et filtres
        return "admin/validations/liste";
    }
}
