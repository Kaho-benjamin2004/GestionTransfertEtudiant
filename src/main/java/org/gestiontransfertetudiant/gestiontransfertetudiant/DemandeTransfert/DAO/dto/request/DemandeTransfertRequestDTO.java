package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class DemandeTransfertRequestDTO {
    @NotBlank(message = "Le motif est obligatoire")
    private String motif;

    @NotNull(message = "L'ID de l'étudiant est obligatoire")
    private UUID etudiantId;
}