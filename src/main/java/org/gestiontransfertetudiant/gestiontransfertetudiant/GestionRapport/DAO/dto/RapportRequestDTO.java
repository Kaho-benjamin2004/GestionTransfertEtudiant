package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.DAO.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RapportRequestDTO {
    @NotBlank(message = "Le type de rapport est obligatoire")
    private String type; // STATS_TRANSFERTS, AUDIT_ACTIONS, etc.
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String format; // PDF, CSV, EXCEL
}