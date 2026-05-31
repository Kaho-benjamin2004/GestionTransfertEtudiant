package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.CONTROLLER;

import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.TransfertExportDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.SERVICE.ITransfertMetier;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.SERVICE.IFraudeMetier;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.SERVICE.IValidationMetier;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/supervision")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SupervisionController {


    private final ITransfertMetier transfertMetier;
    private final IValidationMetier validationMetier;
    private final IFraudeMetier fraudeMetier;
    private final JobLauncher jobLauncher;
    private final Job exportTransfertsJob;

    /**
     * Tableau de bord – indicateurs clés
     */
    @GetMapping("/dashboard")
    public Map<String, Object> dashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        // À implémenter selon vos besoins
        stats.put("totalTransferts", transfertMetier.getTotalTransferts());
        stats.put("transfertsEnCours", transfertMetier.getTransfertsByStatut("EN_COURS_ORIGINE"));
        stats.put("validationsEnAttente", validationMetier.getValidationsEnAttente());
        stats.put("anomaliesCritiques", fraudeMetier.getStatistiques().getAnomaliesCritiques());
        stats.put("tauxFraude", fraudeMetier.getTauxFraude()); // si disponible
        return stats;
    }

    /**
     * Export des transferts au format CSV (appel synchrone)
     */
    @GetMapping("/export/transferts")
    public ResponseEntity<byte[]> exportTransferts(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestParam(required = false) String statut) {

        if (dateDebut == null) dateDebut = LocalDate.now().minusMonths(1);
        if (dateFin == null) dateFin = LocalDate.now();

        List<TransfertExportDTO> data = transfertMetier.getTransfertsForExport(dateDebut, dateFin, statut);

        StringBuilder csv = new StringBuilder();
        csv.append("ID,Étudiant,Date,Motif,Statut,UniversitéOrigine,UniversitéCible\n");
        for (TransfertExportDTO t : data) {
            csv.append(t.getId()).append(",")
                    .append(t.getEtudiantNom()).append(" ").append(t.getEtudiantPrenom()).append(",")
                    .append(t.getDateDemande()).append(",")
                    .append(t.getMotif()).append(",")
                    .append(t.getStatut()).append(",")
                    .append(t.getUniversiteOrigine()).append(",")
                    .append(t.getUniversiteCible()).append("\n");
        }

        byte[] bytes = csv.toString().getBytes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=transferts_" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(bytes);
    }

    /**
     * Déclenchement manuel du job d'export (asynchrone)
     */
    @PostMapping("/batch/export-transferts")
    public String declencherBatchExport() throws Exception {
        jobLauncher.run(exportTransfertsJob, new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());
        return "Job d'export déclenché. Le fichier sera généré dans le dossier /rapports.";
    }
}
