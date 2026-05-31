package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto.DetectionResultDTO;

import java.util.List;

public class DetectionResultMapper {

    public static DetectionResultDTO toDTO(boolean fraudeDetectee, List<String> anomalies, String recommandation) {
        return DetectionResultDTO.builder()
                .fraudeDetectee(fraudeDetectee)
                .anomalies(anomalies)
                .recommandation(recommandation)
                .build();
    }
}