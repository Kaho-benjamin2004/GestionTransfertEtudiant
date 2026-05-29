package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.CONTROLLER;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response.CreditResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response.SanctionResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.usecase.IEtudiantMetier;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/etudiant")
public class EtudiantController {

    private final IEtudiantMetier etudiantMetier;

    private UUID getCurrentEtudiantId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        // On suppose que l'étudiant est lié à l'utilisateur via EtudiantRepository.findByUtilisateurId()
        // À implémenter : récupérer l'ID étudiant à partir de l'ID utilisateur
        return etudiantMetier.findEtudiantIdByUtilisateurId(userDetails.getId());
    }

    @GetMapping("/historique")
    public String historique(Model model) {
        UUID etudiantId = getCurrentEtudiantId();
        org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DTO.response.EtudiantResponseDTO etudiant = etudiantMetier.consulterHistorique(etudiantId);
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("pageTitle", "Historique académique");
        model.addAttribute("breadcrumb", "Historique");
        return "etudiant/historique";
    }

    @GetMapping("/credits")
    public String credits(Model model) {
        UUID etudiantId = getCurrentEtudiantId();
        List<CreditResponseDTO> credits = etudiantMetier.consulterCreditsValides(etudiantId);
        model.addAttribute("credits", credits);
        model.addAttribute("pageTitle", "Mes crédits ECTS");
        model.addAttribute("breadcrumb", "Crédits");
        return "etudiant/credits";
    }

    @GetMapping("/sanctions")
    public String sanctions(Model model) {
        UUID etudiantId = getCurrentEtudiantId();
        List<SanctionResponseDTO> sanctions = etudiantMetier.consulterSanctions(etudiantId);
        model.addAttribute("sanctions", sanctions);
        model.addAttribute("pageTitle", "Mes sanctions");
        model.addAttribute("breadcrumb", "Sanctions");
        return "etudiant/sanctions";
    }

    @GetMapping("/etablissements")
    public String etablissements(Model model) {
        UUID etudiantId = getCurrentEtudiantId();
        org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DTO.response.EtudiantResponseDTO etudiant = etudiantMetier.consulterHistorique(etudiantId);
        model.addAttribute("etablissements", etudiant.getEtablissementsAnterieurs());
        model.addAttribute("pageTitle", "Établissements précédents");
        model.addAttribute("breadcrumb", "Établissements");
        return "etudiant/etablissements";
    }
}