package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String type; // "Bearer"
    private Long expiresIn; // secondes
    private UUID utilisateurId;
    private String login;
    private String nom;
    private String prenom;
    private String email;
    private List<String> roles;
}