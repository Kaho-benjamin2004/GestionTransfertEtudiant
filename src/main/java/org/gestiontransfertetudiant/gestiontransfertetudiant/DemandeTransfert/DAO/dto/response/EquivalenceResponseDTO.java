package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class EquivalenceResponseDTO {
    private UUID id;
    private String commentaire;
    private String statut; // VALIDEE, REJETEE
    private List<CoursEquivalentResponseDTO> coursEquivalents;
}
