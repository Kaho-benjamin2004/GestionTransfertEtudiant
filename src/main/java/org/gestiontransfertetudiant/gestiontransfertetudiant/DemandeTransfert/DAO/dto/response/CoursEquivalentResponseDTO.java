package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class CoursEquivalentResponseDTO {
    private UUID id;
    private String codeCoursOrigine;
    private String intituleCoursOrigine;
    private String codeCoursCible;
    private String intituleCoursCible;
    private Integer credits;
}