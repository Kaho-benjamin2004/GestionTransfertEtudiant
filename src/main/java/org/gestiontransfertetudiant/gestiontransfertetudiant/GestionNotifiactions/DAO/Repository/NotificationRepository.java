package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.Repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByDestinataireIdOrderByDateEnvoiDesc(UUID destinataireId);
    Page<Notification> findByDestinataireIdAndArchivedFalse(UUID destinataireId, Pageable pageable);
    long countByDestinataireIdAndLuFalse(UUID destinataireId);

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.lu = true, n.luDate = CURRENT_TIMESTAMP WHERE n.id = :id AND n.destinataireId = :userId")
    int marquerCommeLue(@Param("id") UUID id, @Param("userId") UUID userId);
}