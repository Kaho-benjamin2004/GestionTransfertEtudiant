package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class StatistiquesDTO {
    private String filiere;
    private String anneeUniversitaire;
    private Integer nombreEtudiants;
    private BigDecimal moyenneGenerale;
    private Integer tauxReussite;               // en pourcentage
    private Map<String, Integer> repartitionParNiveau; // ex: "L1": 45, "L2": 38
    private Integer totalCreditsAttribues;
}