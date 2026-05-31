package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class StatistiqueTransfertDTO {
    private LocalDate date;
    private Integer nbDemandes;
    private Double tauxValidation;
}