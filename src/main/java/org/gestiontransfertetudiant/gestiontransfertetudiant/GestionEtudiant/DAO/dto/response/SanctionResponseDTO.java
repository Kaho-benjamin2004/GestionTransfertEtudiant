package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class SanctionResponseDTO {
    private UUID id;
    private String description;
    private LocalDate date;
    private String gravite;
    private Integer duree;
}