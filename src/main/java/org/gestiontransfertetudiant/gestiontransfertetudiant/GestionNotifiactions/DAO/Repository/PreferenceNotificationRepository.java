package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.Repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.entity.PreferenceNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PreferenceNotificationRepository extends JpaRepository<PreferenceNotification, UUID> {
    List<PreferenceNotification> findByUtilisateurId(UUID utilisateurId);
    Optional<PreferenceNotification> findByUtilisateurIdAndTypeEvenementAndCanal(UUID utilisateurId, String typeEvenement, String canal);
}
