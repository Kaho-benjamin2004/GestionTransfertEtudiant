package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class SanctionRequestDTO {
    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "La date est obligatoire")
    @PastOrPresent(message = "La date ne peut être future")
    private LocalDate date;

    @NotBlank(message = "La gravité est obligatoire")
    private String gravite;

    @Min(value = 0, message = "La durée doit être >= 0")
    private Integer duree;

    @NotNull(message = "L'ID étudiant est obligatoire")
    private UUID etudiantId;
}