package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ComparaisonProgrammeDTO {
    private UniteEnseignementResponseDTO unite1;
    private UniteEnseignementResponseDTO unite2;
    private List<String> similitudes;
    private List<String> differences;
    private Integer equivalencesCredits;   // nombre de crédits équivalents
}
