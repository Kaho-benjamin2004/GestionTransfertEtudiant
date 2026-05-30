package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.CONTROLLER;

import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.SERVICE.ITransfertMetier;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/transferts")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminTransfertController {

    private final ITransfertMetier transfertMetier;

    @GetMapping("/audit")
    public String auditTransferts(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size,
                                  Model model) {
        model.addAttribute("demandes", transfertMetier.auditerTransferts(PageRequest.of(page, size)));
        return "admin/transferts/audit";
    }

    @PostMapping("/detecter-doublons")
    public String detecterDoublons(@RequestParam String numeroEtudiant, RedirectAttributes redirectAttributes) {
        try {
            transfertMetier.detecterDoublons(numeroEtudiant);
            redirectAttributes.addFlashAttribute("success", "Aucun doublon détecté.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/transferts/audit";
    }
}