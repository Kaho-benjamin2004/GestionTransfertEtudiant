package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class VerificationResponseDTO {
    private UUID id;
    private LocalDateTime dateVerification;
    private Boolean resultat;
    private String commentaire;
    private UUID verificateurId;
}