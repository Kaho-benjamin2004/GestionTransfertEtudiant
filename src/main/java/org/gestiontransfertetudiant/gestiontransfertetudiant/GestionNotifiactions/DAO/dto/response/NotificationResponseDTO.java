package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class NotificationResponseDTO {
    private UUID id;
    private UUID destinataireId;
    private String type;
    private String titre;
    private String message;
    private LocalDateTime dateEnvoi;
    private Boolean lu;
    private LocalDateTime luDate;
    private Boolean archived;
    private String lien;
}
