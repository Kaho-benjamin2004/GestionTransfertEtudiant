package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class DocumentResponseDTO {
    private UUID id;
    private String nom;
    private String type;
    private String cheminStockage;
    private String hash;
    private LocalDateTime dateDepot;
    private String statut;
    private List<VersionDocumentResponseDTO> versions;
    private List<VerificationResponseDTO> verifications;
}