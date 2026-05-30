package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationStatutResponseDTO {
    private String entiteType;
    private Integer etapeCourante;
    private String statutGlobal;
    private Boolean estTerminee;
}