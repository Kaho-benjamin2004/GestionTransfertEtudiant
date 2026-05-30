package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.CONTROLLER;

import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.SERVICE.ITransfertMetier;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/universite/origine")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('UNIV_A', 'ADMIN')")
public class UniversiteOrigineController {

    private final ITransfertMetier transfertMetier;

    @PostMapping("/demandes/{id}/verifier-eligibilite")
    public String verifierEligibilite(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            transfertMetier.verifierEligibilite(id);
            redirectAttributes.addFlashAttribute("success", "Éligibilité vérifiée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/universite/origine/demandes";
    }

    @PostMapping("/demandes/{id}/valider")
    public String validerDemande(@PathVariable UUID id,
                                 @RequestParam boolean accepte,
                                 @RequestParam(required = false) String commentaire,
                                 RedirectAttributes redirectAttributes) {
        try {
            // Récupérer l'ID du responsable connecté (à implémenter)
            UUID responsableId = getCurrentUserId();
            transfertMetier.validerParUniversiteOrigine(id, accepte, commentaire, responsableId);
            redirectAttributes.addFlashAttribute("success", "Avis enregistré.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/universite/origine/demandes";
    }

    private UUID getCurrentUserId() {
        // Implémentation à partir de SecurityContextHolder
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}