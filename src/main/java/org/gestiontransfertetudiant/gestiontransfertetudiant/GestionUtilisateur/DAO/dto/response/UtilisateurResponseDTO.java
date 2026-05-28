package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Builder
public class UtilisateurResponseDTO {
      private UUID id;
            private String login;
            private boolean actif;
    private LocalDateTime dateCreation;
            private LocalDateTime derniereConnexion;
            private boolean estBloque;

}
