package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.CONTROLLER;

import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request.CoursEquivalentRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request.EquivalenceRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.SERVICE.ITransfertMetier;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/universite/accueil")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('UNIV_B', 'ADMIN')")
public class UniversiteAccueilController {

    private final ITransfertMetier transfertMetier;

    @GetMapping("/demandes/{id}/analyser-equivalences")
    public String formAnalyserEquivalences(@PathVariable UUID id, Model model) {
        model.addAttribute("demandeId", id);
        model.addAttribute("equivalenceRequest", new EquivalenceRequestDTO());
        return "universite/accueil/analyser-equivalences";
    }

    @PostMapping("/demandes/{id}/analyser-equivalences")
    public String analyserEquivalences(@PathVariable UUID id,
                                       @ModelAttribute EquivalenceRequestDTO request,
                                       RedirectAttributes redirectAttributes) {
        try {
            UUID responsableId = getCurrentUserId();
            transfertMetier.analyserEquivalences(id, request, responsableId);
            redirectAttributes.addFlashAttribute("success", "Analyse d'équivalence enregistrée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/universite/accueil/demandes";
    }

    @PostMapping("/demandes/{id}/valider")
    public String validerDemande(@PathVariable UUID id,
                                 @RequestParam boolean accepte,
                                 @RequestParam(required = false) String commentaire,
                                 RedirectAttributes redirectAttributes) {
        try {
            UUID responsableId = getCurrentUserId();
            transfertMetier.validerParUniversiteAccueil(id, accepte, commentaire, responsableId);
            redirectAttributes.addFlashAttribute("success", "Décision enregistrée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/universite/accueil/demandes";
    }

    @PostMapping("/demandes/{id}/cours-reprise")
    public String definirCoursReprise(@PathVariable UUID id,
                                      @RequestBody List<CoursEquivalentRequestDTO> coursAReprendre,
                                      RedirectAttributes redirectAttributes) {
        try {
            UUID responsableId = getCurrentUserId();
            transfertMetier.definirCoursReprise(id, coursAReprendre, responsableId);
            redirectAttributes.addFlashAttribute("success", "Cours à reprendre définis.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/universite/accueil/demandes";
    }

    private UUID getCurrentUserId() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}