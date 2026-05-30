package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.reponse;

import lombok.Builder;
import lombok.Data;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.reponse.UniteEnseignementResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class NoteResponseDTO {
    private UUID id;
    private BigDecimal valeur;
    private String session;
    private LocalDate dateObtention;
    private UniteEnseignementResponseDTO uniteEnseignement;
}
