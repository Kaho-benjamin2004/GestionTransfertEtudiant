package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AlerteResponseDTO {
    private UUID id;
    private LocalDateTime dateCreation;
    private String type;
    private String niveau;
    private String message;
    private UUID entiteId;
    private String entiteType;
    private Boolean traitee;
    private String commentaireTraitement;
}