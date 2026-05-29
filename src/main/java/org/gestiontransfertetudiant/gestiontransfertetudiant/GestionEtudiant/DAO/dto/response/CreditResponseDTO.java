package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class CreditResponseDTO {
    private UUID id;
    private Integer nombre;
    private String annee;
    private String statut;
}