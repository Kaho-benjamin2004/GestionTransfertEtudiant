package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NoteRequestDTO {
    @NotNull(message = "La valeur est obligatoire")
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "20.0", inclusive = true)
    private BigDecimal valeur;

    private String session; // "Normale", "Rattrapage"

    @PastOrPresent(message = "La date ne peut être future")
    private LocalDate dateObtention;

    @NotNull(message = "L'ID du parcours académique est obligatoire")
    private UUID parcoursAcademiqueId;

    @NotNull(message = "L'ID de l'unité d'enseignement est obligatoire")
    private UUID uniteEnseignementId;
}