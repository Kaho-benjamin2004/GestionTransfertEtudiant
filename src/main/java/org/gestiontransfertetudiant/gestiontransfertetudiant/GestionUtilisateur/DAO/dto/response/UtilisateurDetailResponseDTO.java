package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UtilisateurDetailResponseDTO {
    private UUID id;
    private String login;
    private Boolean actif;
    private ProfilResponseDTO profil;
    private List<RoleResponseDTO> roles;
    private List<PermissionResponseDTO> permissions;
}
