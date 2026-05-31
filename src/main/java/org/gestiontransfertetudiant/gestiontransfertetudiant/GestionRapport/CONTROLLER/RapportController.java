package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.CONTROLLER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.DAO.dto.RapportRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.DAO.repository.RapportRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.RapportService;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.rRapportService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Controller
@RequestMapping("/admin/rapports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RapportController {

    private final rRapportService rapportService;
    private final RapportRepository rapportRepository;

    @GetMapping("/generer")
    public String formGenererRapport(Model model) {
        if (!model.containsAttribute("rapportRequest")) {
            model.addAttribute("rapportRequest", new RapportRequestDTO());
        }
        return "admin/rapports/generer";
    }

    @PostMapping("/generer")
    public String genererRapport(@Valid @ModelAttribute("rapportRequest") RapportRequestDTO request,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/rapports/generer";
        }
        try {
            LocalDateTime debut = request.getDateDebut() != null ? request.getDateDebut().atStartOfDay() : null;
            LocalDateTime fin = request.getDateFin() != null ? request.getDateFin().atTime(LocalTime.MAX) : null;
            rapportService.genererRapport(request.getType(), debut, fin, request.getFormat(), "/tmp/rapports");
            redirectAttributes.addFlashAttribute("success", "Génération du rapport lancée (asynchrone).");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/rapports/liste";
    }

    @GetMapping("/liste")
    public String listeRapports(Model model) {
        model.addAttribute("rapports", rapportRepository.findAll());
        return "admin/rapports/liste";
    }

    @GetMapping("/telecharger/{id}")
    public ResponseEntity<Resource> telechargerRapport(@PathVariable UUID id) throws Exception {
        var rapport = rapportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rapport non trouvé"));
        Path filePath = Paths.get(rapport.getChemin());
        Resource resource = new UrlResource(filePath.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + rapport.getNom() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
