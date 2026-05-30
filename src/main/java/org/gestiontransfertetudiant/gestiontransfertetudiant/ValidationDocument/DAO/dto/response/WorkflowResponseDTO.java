package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class WorkflowResponseDTO {
    private UUID id;
    private String nom;
    private String description;
    private Boolean actif;
    private List<EtapeValidationResponseDTO> etapes;
}