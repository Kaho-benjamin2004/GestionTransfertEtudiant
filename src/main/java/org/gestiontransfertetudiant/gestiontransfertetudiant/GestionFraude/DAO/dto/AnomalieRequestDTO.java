package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AnomalieRequestDTO {
    @NotBlank(message = "Le type d'anomalie est obligatoire")
    private String typeAnomalie; // DOUBLON, INCOHERENCE, etc.

    @NotBlank(message = "Le niveau est obligatoire (INFO, AVERTISSEMENT, CRITIQUE)")
    private String niveau;

    @NotBlank(message = "Le message est obligatoire")
    private String message;

    @NotNull(message = "L'ID de l'entité concernée est obligatoire")
    private UUID entiteConcerneeId;

    @NotBlank(message = "Le type d'entité est obligatoire (DEMANDE_TRANSFERT, DOCUMENT, etc.)")
    private String entiteType;
}