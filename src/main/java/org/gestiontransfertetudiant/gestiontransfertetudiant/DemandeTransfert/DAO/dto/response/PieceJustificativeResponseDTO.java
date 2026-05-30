package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PieceJustificativeResponseDTO {
    private UUID id;
    private String nomFichier;
    private String type;
    private String chemin; // URL pour téléchargement
    private LocalDateTime dateUpload;
}