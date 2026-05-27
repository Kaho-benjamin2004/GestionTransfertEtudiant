package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class PermissionResponseDTO {
    private UUID id;
    private String nom;
    private String ressource;
    private String action;
}