package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ValidationResponseDTO {
    private UUID id;
    private UUID entiteId;
    private String entiteType;
    private Integer etape;
    private String statut;        // EN_ATTENTE, VALIDEE, REJETEE, SUSPENDUE
    private LocalDateTime dateSoumission;
    private LocalDateTime dateValidation;
    private String commentaire;
    private UUID valideurId;
    private List<HistoriqueValidationResponseDTO> historiques;
}