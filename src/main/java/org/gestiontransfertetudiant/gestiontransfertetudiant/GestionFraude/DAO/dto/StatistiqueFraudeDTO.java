package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class StatistiqueFraudeDTO {
    private Long totalAnomalies;
    private Long anomaliesResolues;
    private Long anomaliesCritiques;
    private Map<String, Long> anomaliesParType; // type -> nombre
    private Map<String, Long> anomaliesParEntite; // entiteType -> nombre
}
