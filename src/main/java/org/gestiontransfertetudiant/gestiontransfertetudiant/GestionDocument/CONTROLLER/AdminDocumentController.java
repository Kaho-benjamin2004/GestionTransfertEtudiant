package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.CONTROLLER;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.SERVICE.IDocumentMetier;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;


@Controller
@RequestMapping("/admin/documents")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'AGENT', 'UNIV_A', 'UNIV_B')")
public class AdminDocumentController {

    private final IDocumentMetier documentMetier;

    private UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getId();
    }

    @GetMapping("/verifier/{id}")
    public String verifierAuthenticite(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            documentMetier.verifierAuthenticite(id, getCurrentUserId());
            redirectAttributes.addFlashAttribute("success", "Authenticité vérifiée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/documents/liste";
    }

    @PostMapping("/signer/{id}")
    public String signerElectroniquement(@PathVariable UUID id,
                                         @RequestParam String signature,
                                         RedirectAttributes redirectAttributes) {
        try {
            documentMetier.signerElectroniquement(id, signature);
            redirectAttributes.addFlashAttribute("success", "Document signé électroniquement.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/documents/liste";
    }

    @PostMapping("/archiver/{id}")
    public String archiverDocument(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            documentMetier.archiverDocument(id);
            redirectAttributes.addFlashAttribute("success", "Document archivé.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/documents/liste";
    }

    @GetMapping("/detecter-falsification/{id}")
    public String detecterFalsification(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            documentMetier.detecterFalsification(id);
            redirectAttributes.addFlashAttribute("success", "Aucune falsification détectée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/documents/liste";
    }

    @GetMapping("/liste")
    public String listerTousDocuments(Model model) {
        // Cette méthode peut être enrichie pour afficher tous les documents avec pagination
        int page = 0;
        int size = 0;
        model.addAttribute("documents", documentMetier.listerDocumentsParProprietaire(null, PageRequest.of(page, size))); // à adapter
        return "admin/documents/liste";
    }
}