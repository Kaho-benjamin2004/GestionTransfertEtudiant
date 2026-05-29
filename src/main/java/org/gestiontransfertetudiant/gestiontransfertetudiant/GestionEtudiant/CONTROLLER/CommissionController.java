package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.CONTROLLER;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.usecase.IEtudiantMetier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/commission/programmes")
@PreAuthorize("hasRole('COMMISSION') or hasRole('ADMIN')")
public class CommissionController {

    private final IEtudiantMetier etudiantMetier;

    @GetMapping("/comparer")
    public String comparerForm() {
        return "commission/comparer_form";
    }

    @GetMapping("/comparer/resultat")
    public String comparer(@RequestParam UUID ue1, @RequestParam UUID ue2, Model model) {
        var comparaison = etudiantMetier.comparerProgrammes(ue1, ue2);
        model.addAttribute("comparaison", comparaison);
        return "commission/comparer_resultat";
    }
}