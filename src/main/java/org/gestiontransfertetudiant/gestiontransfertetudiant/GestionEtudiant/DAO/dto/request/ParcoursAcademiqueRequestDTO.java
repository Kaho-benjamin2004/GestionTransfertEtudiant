package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ParcoursAcademiqueRequestDTO {
    @NotBlank(message = "L'année universitaire est obligatoire")
    @Pattern(regexp = "^\\d{4}-\\d{4}$", message = "Format attendu : YYYY-YYYY")
    private String anneeUniversitaire;

    private String statut; // "En cours", "Validé", "Échoué"

    @NotNull(message = "L'ID étudiant est obligatoire")
    private UUID etudiantId;
}
