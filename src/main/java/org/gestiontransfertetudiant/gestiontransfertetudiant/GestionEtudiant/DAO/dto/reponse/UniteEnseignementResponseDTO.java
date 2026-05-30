package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.reponse;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class UniteEnseignementResponseDTO {
    private UUID id;
    private String code;
    private String intitule;
    private Integer credits;
    private Integer coefficient;
}