package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.SERVICE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.Repository.NotificationRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.Repository.PreferenceNotificationRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.mapper.NotificationMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.mapper.PreferenceNotificationMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.request.NotificationRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.request.PreferenceNotificationRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.response.NotificationResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.response.PreferenceNotificationResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.entity.Notification;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.entity.PreferenceNotification;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.SERVICE.INotificationMetier;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.config.NotificationDispatcher;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.BusinessException;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationMetierImpl implements INotificationMetier {

    private final NotificationRepository notificationRepository;
    private final NotificationDispatcher notificationDispatcher;
    private final PreferenceNotificationRepository preferenceRepository;


    @Override
    @Async
    public void envoyerNotification(NotificationRequestDTO request) throws BusinessException {
        // Vérifier les préférences (déjà fait dans le dispatcher, donc on peut simplifier)
        // Enregistrer en base
        Notification notification = NotificationMapper.toEntity(request);
        notification = notificationRepository.save(notification);
        // Envoyer via le dispatcher (asynchrone)
        notificationDispatcher.envoyer(request);
        log.info("Notification {} enregistrée et dispatchée", notification.getId());
    }
//
//    @Override
//    @Async
//    public void envoyerNotification(NotificationRequestDTO request) throws BusinessException {
//        // Vérifier les préférences de l'utilisateur
//        List<PreferenceNotification> prefs = preferenceRepository.findByUtilisateurId(request.getDestinataireId());
//        boolean peutEnvoyer = prefs.stream()
//                .anyMatch(p -> p.getCanal().equals(request.getType()) && p.getActif());
//        if (!peutEnvoyer) {
//            log.info("Notification non envoyée (préférence désactivée) pour utilisateur {}", request.getDestinataireId());
//            return;
//        }
//        Notification notification = NotificationMapper.toEntity(request);
//        notification = notificationRepository.save(notification);
//        // Ici, implémenter l'envoi réel (email, SMS, etc.) via des services dédiés
//        log.info("Notification {} envoyée à {}", notification.getId(), request.getDestinataireId());
//    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponseDTO> getNotificationsByUser(UUID userId, Pageable pageable) {
        return notificationRepository.findByDestinataireIdAndArchivedFalse(userId, pageable)
                .map(NotificationMapper::toDTO);
    }

    @Override
    public void marquerCommeLue(UUID notificationId, UUID userId) throws ResourceNotFoundException {
        int updated = notificationRepository.marquerCommeLue(notificationId, userId);
        if (updated == 0) {
            throw new ResourceNotFoundException("Notification non trouvée ou non accessible", notificationId);
        }
    }

    @Override
    public void archiverNotification(UUID notificationId, UUID userId) throws ResourceNotFoundException {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", notificationId));
        if (!notification.getDestinataireId().equals(userId)) {
            throw new BusinessException("Vous n'êtes pas autorisé à archiver cette notification", "FORBIDDEN");
        }
        notification.setArchived(true);
        notificationRepository.save(notification);
    }

    @Override
    public void supprimerNotification(UUID notificationId, UUID userId) throws ResourceNotFoundException {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", notificationId));
        if (!notification.getDestinataireId().equals(userId)) {
            throw new BusinessException("Vous n'êtes pas autorisé à supprimer cette notification", "FORBIDDEN");
        }
        notificationRepository.delete(notification);
    }

    @Override
    public void configurerPreference(UUID userId, PreferenceNotificationRequestDTO request) throws BusinessException {
        PreferenceNotification pref = preferenceRepository
                .findByUtilisateurIdAndTypeEvenementAndCanal(userId, request.getTypeEvenement(), request.getCanal())
                .orElse(null);
        if (pref == null) {
            pref = PreferenceNotificationMapper.toEntity(request, userId);
        } else {
            pref.setActif(request.getActif() != null ? request.getActif() : true);
        }
        preferenceRepository.save(pref);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreferenceNotificationResponseDTO> getPreferencesByUser(UUID userId) {
        return preferenceRepository.findByUtilisateurId(userId).stream()
                .map(PreferenceNotificationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long getNombreNonLues(UUID userId) {
        return notificationRepository.countByDestinataireIdAndLuFalse(userId);
    }
}
