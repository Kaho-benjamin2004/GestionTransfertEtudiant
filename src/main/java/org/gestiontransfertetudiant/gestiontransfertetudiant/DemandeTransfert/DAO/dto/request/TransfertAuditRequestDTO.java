package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class TransfertAuditRequestDTO {
    @NotBlank(message = "L'action est obligatoire")
    private String action;

    private String details;

    private UUID utilisateurId; // optionnel, peut être déduit du contexte
}