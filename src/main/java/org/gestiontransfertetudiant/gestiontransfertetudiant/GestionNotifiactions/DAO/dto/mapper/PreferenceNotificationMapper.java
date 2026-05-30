package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.request.PreferenceNotificationRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.response.PreferenceNotificationResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.entity.PreferenceNotification;

import java.util.UUID;

public class PreferenceNotificationMapper {

    public static PreferenceNotificationResponseDTO toDTO(PreferenceNotification pref) {
        if (pref == null) return null;
        return PreferenceNotificationResponseDTO.builder()
                .id(pref.getId())
                .utilisateurId(pref.getUtilisateurId())
                .typeEvenement(pref.getTypeEvenement())
                .canal(pref.getCanal())
                .actif(pref.getActif())
                .build();
    }

    public static PreferenceNotification toEntity(PreferenceNotificationRequestDTO requestDTO, UUID utilisateurId) {
        if (requestDTO == null) return null;
        PreferenceNotification pref = new PreferenceNotification();
        pref.setUtilisateurId(utilisateurId);
        pref.setTypeEvenement(requestDTO.getTypeEvenement());
        pref.setCanal(requestDTO.getCanal());
        pref.setActif(requestDTO.getActif() != null ? requestDTO.getActif() : true);
        return pref;
    }
}
