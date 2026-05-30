package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class EquivalenceRequestDTO {
    private String commentaire;

    @NotNull(message = "La liste des cours équivalents est obligatoire")
    private List<CoursEquivalentRequestDTO> coursEquivalents;
}