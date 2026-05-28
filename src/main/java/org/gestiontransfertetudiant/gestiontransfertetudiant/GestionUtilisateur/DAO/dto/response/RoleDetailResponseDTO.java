package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class RoleDetailResponseDTO {
    private UUID id;
    private String nom;
    private String description;
    private Integer nbUtilisateurs;   // ajouté
    private List<PermissionResponseDTO> permissions;
}