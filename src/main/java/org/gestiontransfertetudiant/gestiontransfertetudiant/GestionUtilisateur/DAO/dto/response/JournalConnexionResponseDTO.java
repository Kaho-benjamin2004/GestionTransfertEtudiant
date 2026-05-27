package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class JournalConnexionResponseDTO {
    private UUID id;
    private LocalDateTime dateHeure;
    private Boolean succes;
    private String adresseIP;
    private String raisonEchec;
    private UUID utilisateurId;
}