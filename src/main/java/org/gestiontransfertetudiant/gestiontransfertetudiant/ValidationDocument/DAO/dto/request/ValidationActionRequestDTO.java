package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ValidationActionRequestDTO {
    @NotBlank(message = "Le commentaire est obligatoire")
    private String commentaire;

    private UUID valideurId; // optionnel, peut être déduit du contexte
}