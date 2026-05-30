package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class TransfertAuditResponseDTO {
    private UUID id;
    private String action;
    private LocalDateTime dateAction;
    private UUID utilisateurId;
    private String utilisateurLogin; // dénormalisé
    private String details;
}