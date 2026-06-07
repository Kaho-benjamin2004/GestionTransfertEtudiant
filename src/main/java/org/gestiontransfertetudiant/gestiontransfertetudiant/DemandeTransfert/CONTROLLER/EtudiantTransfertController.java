////package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.CONTROLLER;
////
////import jakarta.validation.Valid;
////import lombok.RequiredArgsConstructor;
////
////import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request.DemandeTransfertRequestDTO;
////import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request.PieceJustificativeRequestDTO;
////import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.SERVICE.ITransfertMetier;
////import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.IEtudiantMetier;
////import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
////import org.springframework.security.access.prepost.PreAuthorize;
////import org.springframework.security.core.Authentication;
////import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.stereotype.Controller;
////import org.springframework.ui.Model;
////import org.springframework.validation.BindingResult;
////import org.springframework.web.bind.annotation.*;
////import org.springframework.web.multipart.MultipartFile;
////import org.springframework.web.servlet.mvc.support.RedirectAttributes;
////
////
////import java.util.UUID;
////
////@Controller
////@RequestMapping("/etudiant/transferts")
////@RequiredArgsConstructor
////@PreAuthorize("hasRole('ETUDIANT')")
////public class EtudiantTransfertController {
////
////    private final ITransfertMetier transfertMetier;
////    private final IEtudiantMetier etudiantMetier;
////
////    private UUID getCurrentEtudiantId() {
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
////        // Ici il faudrait récupérer l'étudiant associé à l'utilisateur
////        // On suppose une méthode dans le service du Module 2 ou une table de liaison
////        // Pour l'exemple, on retourne un UUID factice ; en réalité, il faut l'implémenter
////        return etudiantMetier.getEtudiantIdByUtilisateurId(userDetails.getId());
////    }
////
////    @GetMapping("/creer")
////    public String formCreerDemande(Model model) {
////        if (!model.containsAttribute("demandeRequest")) {
////            model.addAttribute("demandeRequest", new DemandeTransfertRequestDTO());
////        }
////        return "etudiant/transferts/creer";
////    }
////
////    @PostMapping("/creer")
////    public String creerDemande(@Valid @ModelAttribute("demandeRequest") DemandeTransfertRequestDTO request,
////                               BindingResult result,
////                               RedirectAttributes redirectAttributes) {
////        if (result.hasErrors()) {
////            return "etudiant/transferts/creer";
////        }
////        try {
////            request.setEtudiantId(getCurrentEtudiantId());
////            transfertMetier.creerDemande(request);
////            redirectAttributes.addFlashAttribute("success", "Demande créée avec succès.");
////            return "redirect:/etudiant/transferts/mes-demandes";
////        } catch (Exception e) {
////            redirectAttributes.addFlashAttribute("error", e.getMessage());
////            return "redirect:/etudiant/transferts/creer";
////        }
////    }
////
////    @GetMapping("/mes-demandes")
////    public String mesDemandes(Model model) {
////        UUID etudiantId = getCurrentEtudiantId();
////        model.addAttribute("demandes", transfertMetier.consulterMesDemandes(etudiantId));
////        return "etudiant/transferts/liste";
////    }
////
////    @PostMapping("/{id}/soumettre")
////    public String soumettreDemande(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
////        try {
////            transfertMetier.soumettreDemande(id, getCurrentEtudiantId());
////            redirectAttributes.addFlashAttribute("success", "Demande soumise avec succès.");
////        } catch (Exception e) {
////            redirectAttributes.addFlashAttribute("error", e.getMessage());
////        }
////        return "redirect:/etudiant/transferts/mes-demandes";
////    }
////
////    @PostMapping("/{id}/annuler")
////    public String annulerDemande(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
////        try {
////            transfertMetier.annulerDemande(id, getCurrentEtudiantId());
////            redirectAttributes.addFlashAttribute("success", "Demande annulée.");
////        } catch (Exception e) {
////            redirectAttributes.addFlashAttribute("error", e.getMessage());
////        }
////        return "redirect:/etudiant/transferts/mes-demandes";
////    }
////
////    @GetMapping("/{id}/ajouter-pieces")
////    public String formAjouterPiece(@PathVariable UUID id, Model model) {
////        if (!model.containsAttribute("pieceRequest")) {
////            model.addAttribute("pieceRequest", new PieceJustificativeRequestDTO());
////        }
////        model.addAttribute("demandeId", id);
////        return "etudiant/transferts/ajouter-pieces";
////    }
////
////    @PostMapping("/{id}/ajouter-pieces")
////    public String ajouterPiece(@PathVariable UUID id,
////                               @RequestParam("fichier") MultipartFile fichier,
////                               @RequestParam("type") String type,
////                               RedirectAttributes redirectAttributes) {
////        try {
////            PieceJustificativeRequestDTO request = new PieceJustificativeRequestDTO();
////            request.setType(type);
////            request.setFichier(fichier);
////            transfertMetier.ajouterPieceJustificative(id, request, getCurrentEtudiantId());
////            redirectAttributes.addFlashAttribute("success", "Pièce ajoutée.");
////        } catch (Exception e) {
////            redirectAttributes.addFlashAttribute("error", e.getMessage());
////        }
////        return "redirect:/etudiant/transferts/mes-demandes";
////    }
////}
//package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.CONTROLLER;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request.DemandeTransfertRequestDTO;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request.PieceJustificativeRequestDTO;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.response.DemandeTransfertResponseDTO;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.SERVICE.ITransfertMetier;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.IEtudiantMetier;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.UUID;
//
//@Controller
//@RequestMapping("/etudiant/transferts")
//@RequiredArgsConstructor
//@PreAuthorize("hasRole('ETUDIANT')")
//public class EtudiantTransfertController {
//
//    private final ITransfertMetier transfertMetier;
//    private final IEtudiantMetier etudiantMetier;
//
//    private UUID getCurrentEtudiantId() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
//        return etudiantMetier.getEtudiantIdByUtilisateurId(userDetails.getId());
//    }
//
//    // ========== Formulaire de création ==========
//    @GetMapping("/creer")
//    public String formCreerDemande(Model model) {
//        if (!model.containsAttribute("demandeRequest")) {
//            model.addAttribute("demandeRequest", new DemandeTransfertRequestDTO());
//        }
//        UUID etudiantId = getCurrentEtudiantId();
//        model.addAttribute("etudiant", etudiantMetier.consulterHistorique(etudiantId));
//        return "etudiant/transferts/creer";
//    }
//
//    @PostMapping("/creer")
//    public String creerDemande(@Valid @ModelAttribute("demandeRequest") DemandeTransfertRequestDTO request,
//                               BindingResult result,
//                               RedirectAttributes redirectAttributes) {
//        if (result.hasErrors()) {
//            return "etudiant/transferts/creer";
//        }
//        try {
//            request.setEtudiantId(getCurrentEtudiantId());
//            transfertMetier.creerDemande(request);
//            redirectAttributes.addFlashAttribute("success", "Demande créée avec succès.");
//            return "redirect:/etudiant/transferts/liste";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            return "redirect:/etudiant/transferts/creer";
//        }
//    }
//
//    // ========== Liste des demandes ==========
//    @GetMapping("/mes-demandes")
//    public String mesDemandes(Model model) {
//        UUID etudiantId = getCurrentEtudiantId();
//        model.addAttribute("demandes", transfertMetier.consulterMesDemandes(etudiantId));
//        model.addAttribute("etudiant", etudiantMetier.consulterHistorique(etudiantId));
//        return "etudiant/transferts/liste";
//    }
//
//    // ========== Détail d'une demande ==========
//    @GetMapping("/{id}")
//    public String voirDemande(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
//        try {
//            DemandeTransfertResponseDTO demande = transfertMetier.suivreDemande(id);
//            UUID etudiantId = getCurrentEtudiantId();
//            if (!demande.getEtudiantId().equals(etudiantId)) {
//                redirectAttributes.addFlashAttribute("error", "Vous n'êtes pas autorisé à voir cette demande.");
//                return "redirect:/etudiant/transferts/mes-demandes";
//            }
//            model.addAttribute("demande", demande);
//            model.addAttribute("etudiant", etudiantMetier.consulterHistorique(etudiantId));
//            return "etudiant/transferts/detail";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            return "redirect:/etudiant/transferts/mes-demandes";
//        }
//    }
//
//    // ========== Soumettre une demande ==========
//    @PostMapping("/{id}/soumettre")
//    public String soumettreDemande(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
//        try {
//            transfertMetier.soumettreDemande(id, getCurrentEtudiantId());
//            redirectAttributes.addFlashAttribute("success", "Demande soumise avec succès.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/etudiant/transferts/mes-demandes";
//    }
//
//    // ========== Annuler une demande ==========
//    @PostMapping("/{id}/annuler")
//    public String annulerDemande(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
//        try {
//            transfertMetier.annulerDemande(id, getCurrentEtudiantId());
//            redirectAttributes.addFlashAttribute("success", "Demande annulée.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/etudiant/transferts/mes-demandes";
//    }
//
//    // ========== Ajouter une pièce justificative (formulaire) ==========
//    @GetMapping("/{id}/ajouter-pieces")
//    public String formAjouterPiece(@PathVariable UUID id, Model model) {
//        if (!model.containsAttribute("pieceRequest")) {
//            model.addAttribute("pieceRequest", new PieceJustificativeRequestDTO());
//        }
//        model.addAttribute("demandeId", id);
//        UUID etudiantId = getCurrentEtudiantId();
//        model.addAttribute("etudiant", etudiantMetier.consulterHistorique(etudiantId));
//        return "etudiant/transferts/ajouter-pieces";
//    }
//
//    // ========== Traitement de l'upload d'une pièce ==========
//    @PostMapping("/{id}/ajouter-pieces")
//    public String ajouterPiece(@PathVariable UUID id,
//                               @RequestParam("fichier") MultipartFile fichier,
//                               @RequestParam("type") String type,
//                               RedirectAttributes redirectAttributes) {
//        try {
//            PieceJustificativeRequestDTO request = new PieceJustificativeRequestDTO();
//            request.setType(type);
//            request.setFichier(fichier);
//            transfertMetier.ajouterPieceJustificative(id, request, getCurrentEtudiantId());
//            redirectAttributes.addFlashAttribute("success", "Pièce ajoutée.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/etudiant/transferts/mes-demandes";
//    }
//}
package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.CONTROLLER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request.DemandeTransfertRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request.PieceJustificativeRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.response.DemandeTransfertResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.SERVICE.ITransfertMetier;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.IEtudiantMetier;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/etudiant/transferts")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ETUDIANT')")
public class EtudiantTransfertController {

    private final ITransfertMetier transfertMetier;
    private final IEtudiantMetier etudiantMetier;

    private UUID getCurrentEtudiantId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return etudiantMetier.getEtudiantIdByUtilisateurId(userDetails.getId());
    }

    // ========== Liste des demandes (accueil) ==========
    @GetMapping
    public String mesDemandes(Model model) {
        UUID etudiantId = getCurrentEtudiantId();
        model.addAttribute("demandes", transfertMetier.consulterMesDemandes(etudiantId));
        model.addAttribute("etudiant", etudiantMetier.consulterHistorique(etudiantId));
        return "etudiant/transferts/liste";
    }

    // ========== Formulaire de création ==========
    @GetMapping("/creer")
    public String formCreerDemande(Model model) {
        if (!model.containsAttribute("demandeRequest")) {
            model.addAttribute("demandeRequest", new DemandeTransfertRequestDTO());
        }
        UUID etudiantId = getCurrentEtudiantId();
        model.addAttribute("etudiant", etudiantMetier.consulterHistorique(etudiantId));
        return "etudiant/transferts/creer";
    }

    @PostMapping("/creer")
    public String creerDemande(@Valid @ModelAttribute("demandeRequest") DemandeTransfertRequestDTO request,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "etudiant/transferts/creer";
        }
        try {
            request.setEtudiantId(getCurrentEtudiantId());
            transfertMetier.creerDemande(request);
            redirectAttributes.addFlashAttribute("success", "Demande créée avec succès.");
            return "redirect:/etudiant/transferts";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/etudiant/transferts/creer";
        }
    }

    // ========== Détail d'une demande ==========
    @GetMapping("/{id}")
    public String voirDemande(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        try {
            DemandeTransfertResponseDTO demande = transfertMetier.suivreDemande(id);
            UUID etudiantId = getCurrentEtudiantId();
            if (!demande.getEtudiantId().equals(etudiantId)) {
                redirectAttributes.addFlashAttribute("error", "Vous n'êtes pas autorisé à voir cette demande.");
                return "redirect:/etudiant/transferts";
            }
            model.addAttribute("demande", demande);
            model.addAttribute("etudiant", etudiantMetier.consulterHistorique(etudiantId));
            return "etudiant/transferts/detail";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/etudiant/transferts";
        }
    }

    // ========== Soumettre une demande ==========
    @PostMapping("/{id}/soumettre")
    public String soumettreDemande(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            transfertMetier.soumettreDemande(id, getCurrentEtudiantId());
            redirectAttributes.addFlashAttribute("success", "Demande soumise avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/etudiant/transferts";
    }

    // ========== Annuler une demande ==========
    @PostMapping("/{id}/annuler")
    public String annulerDemande(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            transfertMetier.annulerDemande(id, getCurrentEtudiantId());
            redirectAttributes.addFlashAttribute("success", "Demande annulée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/etudiant/transferts";
    }

    // ========== Ajouter une pièce (formulaire) ==========
    @GetMapping("/{id}/ajouter-pieces")
    public String formAjouterPiece(@PathVariable UUID id, Model model) {
        if (!model.containsAttribute("pieceRequest")) {
            model.addAttribute("pieceRequest", new PieceJustificativeRequestDTO());
        }
        model.addAttribute("demandeId", id);
        UUID etudiantId = getCurrentEtudiantId();
        model.addAttribute("etudiant", etudiantMetier.consulterHistorique(etudiantId));
        return "etudiant/transferts/ajouter-pieces";
    }

    @PostMapping("/{id}/ajouter-pieces")
    public String ajouterPiece(@PathVariable UUID id,
                               @RequestParam("fichier") MultipartFile fichier,
                               @RequestParam("type") String type,
                               RedirectAttributes redirectAttributes) {
        try {
            PieceJustificativeRequestDTO request = new PieceJustificativeRequestDTO();
            request.setType(type);
            request.setFichier(fichier);
            transfertMetier.ajouterPieceJustificative(id, request, getCurrentEtudiantId());
            redirectAttributes.addFlashAttribute("success", "Pièce ajoutée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/etudiant/transferts";
    }
}