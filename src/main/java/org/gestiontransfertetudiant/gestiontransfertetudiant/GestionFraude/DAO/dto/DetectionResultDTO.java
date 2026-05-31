package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class DetectionResultDTO {
    private boolean fraudeDetectee;
    private List<String> anomalies; // liste des descriptions d'anomalies
    private String recommandation;
}