package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.CONTROLLER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.reponse.EtudiantResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request.NoteRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request.SanctionRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.IEtudiantMetier;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.UUID;

@Controller
@RequestMapping("/agent")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('AGENT', 'ADMIN')")
public class AgentController {

    private final IEtudiantMetier etudiantMetier;

    @GetMapping("/recherche")
    public String rechercheForm() {
        return "agent/recherche";
    }

    @GetMapping("/etudiants")
    public String rechercher(@RequestParam(required = false) String critere,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             Model model) {
        model.addAttribute("etudiants", etudiantMetier.rechercherEtudiants(critere, PageRequest.of(page, size)));
        model.addAttribute("critere", critere);
        return "agent/resultats";
    }

    @GetMapping("/etudiants/{id}/dossier")
    public String dossierComplet(@PathVariable UUID id, Model model) {
        EtudiantResponseDTO etudiant = etudiantMetier.consulterDossierComplet(id);
        model.addAttribute("etudiant", etudiant);
        return "agent/dossier";
    }

    @GetMapping("/etudiants/{id}/notes/ajouter")
    public String formAjoutNote(@PathVariable UUID id, Model model) {
        if (!model.containsAttribute("noteRequest")) {
            NoteRequestDTO noteRequest = new NoteRequestDTO();
            // On suppose que l'utilisateur sélectionne le parcours via un champ dans le formulaire
            model.addAttribute("noteRequest", noteRequest);
        }
        model.addAttribute("listeUE", etudiantMetier.listerToutesUE());
        model.addAttribute("etudiantId", id);
        return "agent/notes/ajouter";
    }

    @PostMapping("/notes/ajouter")
    public String ajouterNote(@Valid @ModelAttribute("noteRequest") NoteRequestDTO request,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listeUE", etudiantMetier.listerToutesUE());
            return "agent/notes/ajouter";
        }
        try {
            etudiantMetier.ajouterNote(request);
            redirectAttributes.addFlashAttribute("success", "Note ajoutée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/agent/etudiants/" + request.getParcoursAcademiqueId() + "/dossier";
    }

    @GetMapping("/etudiants/{id}/sanctions/ajouter")
    public String formAjoutSanction(@PathVariable UUID id, Model model) {
        if (!model.containsAttribute("sanctionRequest")) {
            SanctionRequestDTO request = new SanctionRequestDTO();
            request.setEtudiantId(id);
            model.addAttribute("sanctionRequest", request);
        }
        return "agent/sanctions/ajouter";
    }

    @PostMapping("/sanctions/ajouter")
    public String ajouterSanction(@Valid @ModelAttribute("sanctionRequest") SanctionRequestDTO request,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "agent/sanctions/ajouter";
        }
        try {
            etudiantMetier.ajouterSanction(request);
            redirectAttributes.addFlashAttribute("success", "Sanction enregistrée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/agent/etudiants/" + request.getEtudiantId() + "/dossier";
    }

    @PostMapping("/parcours/{id}/valider")
    public String validerParcours(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            etudiantMetier.validerParcours(id);
            redirectAttributes.addFlashAttribute("success", "Parcours validé.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/agent/etudiants/" + id + "/dossier";
    }

    @GetMapping("/releve/{etudiantId}/{parcoursId}")
    public ResponseEntity<byte[]> genererReleve(@PathVariable UUID etudiantId,
                                                @PathVariable UUID parcoursId) {
        byte[] pdf = etudiantMetier.genererReleveNotes(etudiantId, parcoursId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=releve_notes.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}