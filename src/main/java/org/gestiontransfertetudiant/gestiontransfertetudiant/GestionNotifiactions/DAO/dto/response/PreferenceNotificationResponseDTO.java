package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class PreferenceNotificationResponseDTO {
    private UUID id;
    private UUID utilisateurId;
    private String typeEvenement;
    private String canal;
    private Boolean actif;
}
