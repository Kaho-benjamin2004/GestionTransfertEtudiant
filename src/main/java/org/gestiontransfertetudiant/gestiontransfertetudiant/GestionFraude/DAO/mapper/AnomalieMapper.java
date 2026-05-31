package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto.AnomalieRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto.AnomalieResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity.Anomalie;

import java.time.LocalDateTime;

public class AnomalieMapper {

    public static AnomalieResponseDTO toDTO(Anomalie anomalie) {
        if (anomalie == null) return null;
        return AnomalieResponseDTO.builder()
                .id(anomalie.getId())
                .typeAnomalie(anomalie.getTypeAnomalie())
                .niveau(anomalie.getNiveau())
                .message(anomalie.getMessage())
                .dateDetection(anomalie.getDateDetection())
                .entiteConcerneeId(anomalie.getEntiteConcerneeId())
                .entiteType(anomalie.getEntiteType())
                .resolue(anomalie.getResolue())
                .dateResolution(anomalie.getDateResolution())
                .commentaireResolution(anomalie.getCommentaireResolution())
                .build();
    }

    public static Anomalie toEntity(AnomalieRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        Anomalie anomalie = new Anomalie();
        anomalie.setTypeAnomalie(requestDTO.getTypeAnomalie());
        anomalie.setNiveau(requestDTO.getNiveau());
        anomalie.setMessage(requestDTO.getMessage());
        anomalie.setEntiteConcerneeId(requestDTO.getEntiteConcerneeId());
        anomalie.setEntiteType(requestDTO.getEntiteType());
        anomalie.setDateDetection(LocalDateTime.now());
        anomalie.setResolue(false);
        return anomalie;
    }
}