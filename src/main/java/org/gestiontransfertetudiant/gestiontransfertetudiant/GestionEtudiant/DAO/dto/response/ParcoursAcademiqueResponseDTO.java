package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response.CreditResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response.NoteResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ParcoursAcademiqueResponseDTO {
    private UUID id;
    private String anneeUniversitaire;
    private BigDecimal moyenne;
    private String statut;
    private List<NoteResponseDTO> notes;
    private List<CreditResponseDTO> credits;
}
