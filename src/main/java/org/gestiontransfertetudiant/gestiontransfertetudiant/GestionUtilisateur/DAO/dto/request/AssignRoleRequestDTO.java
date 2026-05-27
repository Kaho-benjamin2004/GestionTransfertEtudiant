package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AssignRoleRequestDTO {
    @NotNull(message = "L'ID utilisateur est obligatoire")
    private UUID utilisateurId;

    @NotNull(message = "L'ID rôle est obligatoire")
    private UUID roleId;
}