package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AnomalieResponseDTO {
    private UUID id;
    private String typeAnomalie;
    private String niveau;
    private String message;
    private LocalDateTime dateDetection;
    private UUID entiteConcerneeId;
    private String entiteType;
    private Boolean resolue;
    private LocalDateTime dateResolution;
    private String commentaireResolution;
}