package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.request.NotificationRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.response.NotificationResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.entity.Notification;

import java.time.LocalDateTime;

public class NotificationMapper {

    public static NotificationResponseDTO toDTO(Notification notification) {
        if (notification == null) return null;
        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .destinataireId(notification.getDestinataireId())
                .type(notification.getType())
                .titre(notification.getTitre())
                .message(notification.getMessage())
                .dateEnvoi(notification.getDateEnvoi())
                .lu(notification.getLu())
                .luDate(notification.getLuDate())
                .archived(notification.getArchived())
                .lien(notification.getLien())
                .build();
    }

    public static Notification toEntity(NotificationRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        Notification notification = new Notification();
        notification.setDestinataireId(requestDTO.getDestinataireId());
        notification.setType(requestDTO.getType());
        notification.setTitre(requestDTO.getTitre());
        notification.setMessage(requestDTO.getMessage());
        notification.setLien(requestDTO.getLien());
        notification.setDateEnvoi(LocalDateTime.now());
        notification.setLu(false);
        notification.setArchived(false);
        return notification;
    }
}