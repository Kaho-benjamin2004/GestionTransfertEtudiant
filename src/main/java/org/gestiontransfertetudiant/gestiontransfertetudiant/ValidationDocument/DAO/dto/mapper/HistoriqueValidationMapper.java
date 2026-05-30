package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.response.HistoriqueValidationResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.HistoriqueValidation;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.Validation;

import java.time.LocalDateTime;
import java.util.UUID;

public class HistoriqueValidationMapper {

    public static HistoriqueValidationResponseDTO toDTO(HistoriqueValidation historique) {
        if (historique == null) return null;
        return HistoriqueValidationResponseDTO.builder()
                .id(historique.getId())
                .action(historique.getAction())
                .dateAction(historique.getDateAction())
                .utilisateurId(historique.getUtilisateurId())
                .details(historique.getDetails())
                .build();
    }

    public static HistoriqueValidation toEntity(Validation validation, String action, String details, UUID utilisateurId) {
        if (validation == null) return null;
        HistoriqueValidation historique = new HistoriqueValidation();
        historique.setAction(action);
        historique.setDateAction(LocalDateTime.now());
        historique.setUtilisateurId(utilisateurId);
        historique.setDetails(details);
        historique.setValidation(validation);
        return historique;
    }
}