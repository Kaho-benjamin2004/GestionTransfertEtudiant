package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.SERVICE;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.request.NotificationRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.request.PreferenceNotificationRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.response.NotificationResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.response.PreferenceNotificationResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.BusinessException;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface INotificationMetier {

    // Envoi (asynchrone)
    void envoyerNotification(NotificationRequestDTO request) throws BusinessException;

    // Consultation pour un utilisateur
    Page<NotificationResponseDTO> getNotificationsByUser(UUID userId, Pageable pageable);

    // Marquer comme lue
    void marquerCommeLue(UUID notificationId, UUID userId) throws ResourceNotFoundException;

    // Archiver
    void archiverNotification(UUID notificationId, UUID userId) throws ResourceNotFoundException;

    // Supprimer définitivement
    void supprimerNotification(UUID notificationId, UUID userId) throws ResourceNotFoundException;

    // Gestion des préférences
    void configurerPreference(UUID userId, PreferenceNotificationRequestDTO request) throws BusinessException;

    List<PreferenceNotificationResponseDTO> getPreferencesByUser(UUID userId);

    long getNombreNonLues(UUID userId);
}