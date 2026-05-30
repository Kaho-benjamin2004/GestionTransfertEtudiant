package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.CONTROLLER;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.IEtudiantMetier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/statistiques")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminStatistiquesController {

    private final IEtudiantMetier etudiantMetier;

    @GetMapping("/form")
    public String formulaireStatistiques() {
        return "admin/statistiques/form";
    }

    @GetMapping("/export")
    public String exporter(@RequestParam String filiere,
                           @RequestParam String anneeUniversitaire,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("stats", etudiantMetier.exporterStatistiques(filiere, anneeUniversitaire));
            return "admin/statistiques/resultat";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/statistiques/form";
        }
    }
}