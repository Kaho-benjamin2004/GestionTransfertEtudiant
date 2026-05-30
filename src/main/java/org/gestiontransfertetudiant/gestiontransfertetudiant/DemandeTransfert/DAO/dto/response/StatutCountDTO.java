package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatutCountDTO {
    private String statut;
    private Long count;
}