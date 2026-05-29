package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request.ParcoursAcademiqueRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response.ParcoursAcademiqueResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.ParcoursAcademique;

import java.util.stream.Collectors;

public class ParcoursAcademiqueMapper {

    public static ParcoursAcademiqueResponseDTO toDTO(ParcoursAcademique parcours) {
        if (parcours == null) return null;
        return ParcoursAcademiqueResponseDTO.builder()
                .id(parcours.getId())
                .anneeUniversitaire(parcours.getAnneeUniversitaire())
                .moyenne(parcours.getMoyenne())
                .statut(parcours.getStatut())
                .notes(parcours.getNotes() != null ?
                        parcours.getNotes().stream()
                        .map(NoteMapper::toDTO)
                        .collect(Collectors.toList()) : null)
                .credits(parcours.getCredits() != null ?
                        parcours.getCredits().stream()
                        .map(CreditMapper::toDTO)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public static ParcoursAcademique toEntity(ParcoursAcademiqueRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        ParcoursAcademique parcours = new ParcoursAcademique();
        parcours.setAnneeUniversitaire(requestDTO.getAnneeUniversitaire());
        parcours.setStatut(requestDTO.getStatut());
        return parcours;
    }
}