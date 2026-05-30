package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class EtapeValidationResponseDTO {
    private UUID id;
    private Integer ordre;
    private String roleRequis;
    private String statutMinimumRequis;
}
