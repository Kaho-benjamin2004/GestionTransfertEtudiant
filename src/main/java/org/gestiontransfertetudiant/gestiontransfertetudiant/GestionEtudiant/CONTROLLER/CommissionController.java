package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.CONTROLLER;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.IEtudiantMetier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/commission")
@RequiredArgsConstructor
@PreAuthorize("hasRole('COMMISSION')")
public class CommissionController {

    private final IEtudiantMetier etudiantMetier;

    @GetMapping("/comparer")
    public String formComparaison(Model model) {
        model.addAttribute("listeUE", etudiantMetier.listerToutesUE());
        return "commission/comparaison";
    }

    @PostMapping("/comparer")
    public String comparer(@RequestParam UUID ueId1,
                           @RequestParam UUID ueId2,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("comparaison", etudiantMetier.comparerProgrammes(ueId1, ueId2));
            return "commission/resultat";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/commission/comparer";
        }
    }
}