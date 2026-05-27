package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AssignPermissionRequestDTO {
    @NotNull(message = "L'ID rôle est obligatoire")
    private UUID roleId;

    @NotNull(message = "L'ID permission est obligatoire")
    private UUID permissionId;
}