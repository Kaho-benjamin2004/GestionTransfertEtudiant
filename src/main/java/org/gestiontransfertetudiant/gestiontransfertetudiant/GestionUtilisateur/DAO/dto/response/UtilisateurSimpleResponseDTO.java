package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UtilisateurSimpleResponseDTO {
    private UUID id;
    private String login;
    private String nom;
    private String prenom;
    private String email;   // à ajouter
    private Boolean actif;  // à ajouter
}