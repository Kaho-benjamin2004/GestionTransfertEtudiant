package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.CONTROLLER;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.usecase.IEtudiantMetier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/statistiques")
@PreAuthorize("hasRole('ADMIN')")
public class AdministrationController {

    private final IEtudiantMetier etudiantMetier;

    @GetMapping
    public String statistiques(@RequestParam(defaultValue = "") String filiere,
                               @RequestParam(defaultValue = "") String annee,
                               Model model) {
        var stats = etudiantMetier.exporterStatistiques(filiere, annee);
        model.addAttribute("stats", stats);
        model.addAttribute("filiere", filiere);
        model.addAttribute("annee", annee);
        return "admin/statistiques";
    }
}