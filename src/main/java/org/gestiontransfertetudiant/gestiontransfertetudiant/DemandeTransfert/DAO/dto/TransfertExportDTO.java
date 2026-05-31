package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class TransfertExportDTO {
    private UUID id;
    private String etudiantNom;
    private String etudiantPrenom;
    private LocalDate dateDemande;
    private String motif;
    private String statut;
    private String universiteOrigine;
    private String universiteCible;
}