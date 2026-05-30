package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class DemandeTransfertResponseDTO {
    private UUID id;
    private LocalDate dateDemande;
    private String motif;
    private String statut;
    private String commentaireRefus;
    private UUID etudiantId;
    private String etudiantNom;
    private String etudiantPrenom;
    private List<PieceJustificativeResponseDTO> pieces;
    private EquivalenceResponseDTO equivalence;
    private List<TransfertAuditResponseDTO> audits;
}