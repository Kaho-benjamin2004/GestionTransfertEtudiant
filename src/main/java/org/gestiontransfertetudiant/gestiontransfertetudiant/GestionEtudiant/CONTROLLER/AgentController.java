package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.CONTROLLER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request.NoteRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request.SanctionRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.usecase.IEtudiantMetier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/agent")
public class AgentController {

    private final IEtudiantMetier etudiantMetier;

    @GetMapping("/etudiants/recherche")
    public String rechercheForm(Model model) {
        model.addAttribute("pageTitle", "Rechercher un étudiant");
        model.addAttribute("breadcrumb", "Recherche");
        return "agent/recherche";
    }

    @GetMapping("/etudiants/recherche/resultats")
    public String rechercher(@RequestParam String critere, @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size, Model model) {
        Page<org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DTO.response.EtudiantResponseDTO> resultats = etudiantMetier.rechercherEtudiants(critere, PageRequest.of(page, size));
        model.addAttribute("resultats", resultats);
        model.addAttribute("critere", critere);
        model.addAttribute("pageTitle", "Résultats de recherche");
        model.addAttribute("breadcrumb", "Recherche");
        return "agent/recherche";
    }

    @GetMapping("/etudiants/{id}/dossier")
    public String dossier(@PathVariable UUID id, Model model) {
        org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DTO.response.EtudiantResponseDTO etudiant = etudiantMetier.consulterDossierComplet(id);
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("pageTitle", "Dossier étudiant");
        model.addAttribute("breadcrumb", "Dossier");
        return "agent/dossier";
    }

    @GetMapping("/notes/ajouter")
    public String ajoutNoteForm(Model model) {
        if (!model.containsAttribute("noteRequest")) {
            model.addAttribute("noteRequest", new NoteRequestDTO());
        }
        model.addAttribute("pageTitle", "Ajouter une note");
        model.addAttribute("breadcrumb", "Notes");
        return "agent/ajout_note";
    }

    @PostMapping("/notes/ajouter")
    public String ajouterNote(@Valid @ModelAttribute("noteRequest") NoteRequestDTO request,
                              BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "agent/ajout_note";
        }
        try {
            etudiantMetier.ajouterNote(request);
            redirectAttributes.addFlashAttribute("success", "Note ajoutée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/agent/notes/ajouter";
    }

    @GetMapping("/sanctions/ajouter")
    public String ajoutSanctionForm(Model model) {
        if (!model.containsAttribute("sanctionRequest")) {
            model.addAttribute("sanctionRequest", new SanctionRequestDTO());
        }
        model.addAttribute("pageTitle", "Ajouter une sanction");
        model.addAttribute("breadcrumb", "Sanctions");
        return "agent/ajout_sanction";
    }

    @PostMapping("/sanctions/ajouter")
    public String ajouterSanction(@Valid @ModelAttribute("sanctionRequest") SanctionRequestDTO request,
                                  BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "agent/ajout_sanction";
        }
        try {
            etudiantMetier.ajouterSanction(request);
            redirectAttributes.addFlashAttribute("success", "Sanction enregistrée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/agent/sanctions/ajouter";
    }

    @GetMapping("/releve/{etudiantId}/{parcoursId}")
    public ResponseEntity<byte[]> genererReleve(@PathVariable UUID etudiantId, @PathVariable UUID parcoursId) {
        byte[] pdf = etudiantMetier.genererReleveNotes(etudiantId, parcoursId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=releve_notes.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}