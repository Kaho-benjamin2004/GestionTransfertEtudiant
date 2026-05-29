package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response.UniteEnseignementResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.UniteEnseignement;

public class UniteEnseignementMapper {

    public static UniteEnseignementResponseDTO toDTO(UniteEnseignement ue) {
        if (ue == null) return null;
        return UniteEnseignementResponseDTO.builder()
                .id(ue.getId())
                .code(ue.getCode())
                .intitule(ue.getIntitule())
                .credits(ue.getCredits())
                .coefficient(ue.getCoefficient())
                .build();
    }

    public static UniteEnseignement toEntity(org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DTO.request.UniteEnseignementRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        UniteEnseignement ue = new UniteEnseignement();
        ue.setCode(requestDTO.getCode());
        ue.setIntitule(requestDTO.getIntitule());
        ue.setCredits(requestDTO.getCredits());
        ue.setCoefficient(requestDTO.getCoefficient());
        return ue;
    }
}