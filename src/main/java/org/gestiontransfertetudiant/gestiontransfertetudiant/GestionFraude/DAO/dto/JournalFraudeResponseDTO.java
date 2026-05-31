package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class JournalFraudeResponseDTO {
    private UUID id;
    private LocalDateTime dateExecution;
    private String action; // VERIFICATION_PERIODIQUE, DETECTION_MANUELLE
    private Integer nbAnomaliesDetectees;
    private String details;
    private UUID executeurId;
}