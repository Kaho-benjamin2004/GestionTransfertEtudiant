package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class SessionResponseDTO {
    private UUID id;
    private String token; // éventuellement tronqué pour la sécurité
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private Boolean actif;
    private String adresseIP;
    private String userAgent;
}