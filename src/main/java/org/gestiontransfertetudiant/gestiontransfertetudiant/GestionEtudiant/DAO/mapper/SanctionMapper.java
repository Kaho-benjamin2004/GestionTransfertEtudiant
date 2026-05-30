package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.reponse.SanctionResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request.SanctionRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.Etudiant;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.Sanction;

public class SanctionMapper {

    public static SanctionResponseDTO toDTO(Sanction sanction) {
        if (sanction == null) return null;
        return SanctionResponseDTO.builder()
                .id(sanction.getId())
                .description(sanction.getDescription())
                .date(sanction.getDate())
                .gravite(sanction.getGravite())
                .duree(sanction.getDuree())
                .build();
    }

    public static Sanction toEntity(SanctionRequestDTO requestDTO, Etudiant etudiant) {
        if (requestDTO == null) return null;
        Sanction sanction = new Sanction();
        sanction.setDescription(requestDTO.getDescription());
        sanction.setDate(requestDTO.getDate());
        sanction.setGravite(requestDTO.getGravite());
        sanction.setDuree(requestDTO.getDuree());
        sanction.setEtudiant(etudiant);
        return sanction;
    }
}