package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class VersionDocumentResponseDTO {
    private UUID id;
    private Integer version;
    private String cheminStockage;
    private String hash;
    private LocalDateTime dateModification;
}