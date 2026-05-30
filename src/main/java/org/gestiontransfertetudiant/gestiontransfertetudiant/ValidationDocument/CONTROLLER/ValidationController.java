package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.CONTROLLER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.request.ValidationRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.response.ValidationResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.SERVICE.IValidationMetier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/validations")
@RequiredArgsConstructor
public class ValidationController {

    private final IValidationMetier validationMetier;

    private UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getId();
    }

    @GetMapping("/soumettre")
    @PreAuthorize("hasAnyRole('AGENT', 'ETUDIANT')")
    public String formSoumettreValidation(Model model) {
        if (!model.containsAttribute("validationRequest")) {
            model.addAttribute("validationRequest", new ValidationRequestDTO());
        }
        return "validation/soumettre";
    }

    @PostMapping("/soumettre")
    @PreAuthorize("hasAnyRole('AGENT', 'ETUDIANT')")
    public String soumettreValidation(@Valid @ModelAttribute("validationRequest") ValidationRequestDTO request,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "validation/soumettre";
        }
        try {
            validationMetier.soumettreValidation(request, getCurrentUserId());
            redirectAttributes.addFlashAttribute("success", "Validation soumise avec succès.");
            return "redirect:/validations/mes-soumissions";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/validations/soumettre";
        }
    }

    @GetMapping("/historique")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public String historique(@RequestParam UUID entiteId,
                             @RequestParam String entiteType,
                             Model model) {
        List<ValidationResponseDTO> validations = validationMetier.getHistoriqueValidations(entiteId, entiteType);
        model.addAttribute("validations", validations);
        return "validation/historique";
    }
}