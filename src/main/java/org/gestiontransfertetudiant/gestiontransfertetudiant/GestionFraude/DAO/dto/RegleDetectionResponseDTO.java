package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RegleDetectionResponseDTO {
    private UUID id;
    private String nom;
    private String description;
    private String type;
    private String parametres;
    private Boolean actif;
    private Integer seuil;
}
