package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request.TransfertAuditRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.response.TransfertAuditResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity.DemandeTransfert;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity.TransfertAudit;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransfertAuditMapper {

    public static TransfertAuditResponseDTO toDTO(TransfertAudit audit) {
        if (audit == null) return null;
        return TransfertAuditResponseDTO.builder()
                .id(audit.getId())
                .action(audit.getAction())
                .dateAction(audit.getDateAction())
                .utilisateurId(audit.getUtilisateurId())
                .details(audit.getDetails())
                .build();
    }

    public static TransfertAudit toEntity(TransfertAuditRequestDTO requestDTO, DemandeTransfert demande, UUID utilisateurId) {
        if (requestDTO == null) return null;
        TransfertAudit audit = new TransfertAudit();
        audit.setAction(requestDTO.getAction());
        audit.setDetails(requestDTO.getDetails());
        audit.setDateAction(LocalDateTime.now());
        audit.setUtilisateurId(utilisateurId);
        audit.setDemande(demande);
        return audit;
    }
}