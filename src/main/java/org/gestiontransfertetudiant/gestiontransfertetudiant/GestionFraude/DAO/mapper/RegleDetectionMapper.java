package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto.RegleDetectionRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto.RegleDetectionResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity.RegleDetection;

public class RegleDetectionMapper {

    public static RegleDetectionResponseDTO toDTO(RegleDetection regle) {
        if (regle == null) return null;
        return RegleDetectionResponseDTO.builder()
                .id(regle.getId())
                .nom(regle.getNom())
                .description(regle.getDescription())
                .type(regle.getType())
//                .expression(regle.getExpression())
                .actif(regle.getActif())
                .build();
    }

    public static RegleDetection toEntity(RegleDetectionRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        RegleDetection regle = new RegleDetection();
        regle.setNom(requestDTO.getNom());
        regle.setDescription(requestDTO.getDescription());
        regle.setType(requestDTO.getType());
//        regle.setExpression(requestDTO.getExpression());
        regle.setActif(requestDTO.getActif() != null ? requestDTO.getActif() : true);
        return regle;
    }
}