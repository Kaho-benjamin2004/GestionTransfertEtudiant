package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class EtablissementAnterieurResponseDTO {
    private UUID id;
    private String nom;
    private String ville;
    private String pays;
    private Integer anneeDebut;
    private Integer anneeFin;
}
