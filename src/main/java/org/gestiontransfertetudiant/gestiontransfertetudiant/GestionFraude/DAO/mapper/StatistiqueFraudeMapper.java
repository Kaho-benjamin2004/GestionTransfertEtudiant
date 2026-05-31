package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto.StatistiqueFraudeDTO;

import java.util.Map;

public class StatistiqueFraudeMapper {

    public static StatistiqueFraudeDTO toDTO(Long totalAnomalies, Long anomaliesResolues, Long anomaliesCritiques,
                                             Map<String, Long> anomaliesParType, Map<String, Long> anomaliesParEntite) {
        return StatistiqueFraudeDTO.builder()
                .totalAnomalies(totalAnomalies)
                .anomaliesResolues(anomaliesResolues)
                .anomaliesCritiques(anomaliesCritiques)
                .anomaliesParType(anomaliesParType)
                .anomaliesParEntite(anomaliesParEntite)
                .build();
    }
}
