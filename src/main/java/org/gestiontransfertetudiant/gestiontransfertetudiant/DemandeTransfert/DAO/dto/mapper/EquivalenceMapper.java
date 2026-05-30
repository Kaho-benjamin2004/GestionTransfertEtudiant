package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request.EquivalenceRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.response.EquivalenceResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity.DemandeTransfert;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity.Equivalence;

import java.util.stream.Collectors;

public class EquivalenceMapper {

    public static EquivalenceResponseDTO toDTO(Equivalence equivalence) {
        if (equivalence == null) return null;
        return EquivalenceResponseDTO.builder()
                .id(equivalence.getId())
                .commentaire(equivalence.getCommentaire())
                .statut(equivalence.getStatut())
                .coursEquivalents(equivalence.getCoursEquivalents() != null ?
                        equivalence.getCoursEquivalents().stream()
                        .map(CoursEquivalentMapper::toDTO)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public static Equivalence toEntity(EquivalenceRequestDTO requestDTO, DemandeTransfert demande) {
        if (requestDTO == null) return null;
        Equivalence equivalence = new Equivalence();
        equivalence.setCommentaire(requestDTO.getCommentaire());
        equivalence.setDemande(demande);
        equivalence.setStatut("EN_ATTENTE"); // ou "VALIDE" après validation
        return equivalence;
    }
}
