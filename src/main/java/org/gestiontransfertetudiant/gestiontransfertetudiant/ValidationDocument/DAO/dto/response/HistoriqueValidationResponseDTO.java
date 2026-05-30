package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class HistoriqueValidationResponseDTO {
    private UUID id;
    private String action;
    private LocalDateTime dateAction;
    private UUID utilisateurId;
    private String details;
}