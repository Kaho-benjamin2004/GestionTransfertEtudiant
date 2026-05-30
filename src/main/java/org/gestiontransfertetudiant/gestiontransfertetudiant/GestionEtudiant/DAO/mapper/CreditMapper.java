package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.reponse.CreditResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request.CreditRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.Credit;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.ParcoursAcademique;

public class CreditMapper {

    public static CreditResponseDTO toDTO(Credit credit) {
        if (credit == null) return null;
        return CreditResponseDTO.builder()
                .id(credit.getId())
                .nombre(credit.getNombre())
                .annee(credit.getAnnee())
                .statut(credit.getStatut())
                .build();
    }

    public static Credit toEntity(CreditRequestDTO requestDTO, ParcoursAcademique parcoursAcademique) {
        if (requestDTO == null) return null;
        Credit credit = new Credit();
        credit.setNombre(requestDTO.getNombre());
        credit.setAnnee(requestDTO.getAnnee());
        credit.setStatut(requestDTO.getStatut());
        credit.setParcoursAcademique(parcoursAcademique);
        return credit;
    }
}