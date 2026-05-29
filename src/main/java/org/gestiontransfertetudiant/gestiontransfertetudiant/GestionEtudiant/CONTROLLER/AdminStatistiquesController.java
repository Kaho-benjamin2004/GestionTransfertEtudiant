package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.CONTROLLER;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response.StatistiquesDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.usecase.IEtudiantMetier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/statistiques")
public class AdminStatistiquesController {

    private final IEtudiantMetier etudiantMetier;

    @GetMapping
    public String statistiques(@RequestParam(required = false) String filiere,
                               @RequestParam(required = false) String annee, Model model) {
        if (filiere != null && annee != null) {
            StatistiquesDTO stats = etudiantMetier.exporterStatistiques(filiere, annee);
            model.addAttribute("stats", stats);
        }
        model.addAttribute("pageTitle", "Statistiques académiques");
        model.addAttribute("breadcrumb", "Statistiques");
        return "admin/statistiques";
    }
}