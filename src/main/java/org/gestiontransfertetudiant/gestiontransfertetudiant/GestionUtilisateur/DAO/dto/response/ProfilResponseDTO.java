package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class ProfilResponseDTO {
    private UUID id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String matriculeNational;
    private String fonction;
    private UUID utilisateurId;
}