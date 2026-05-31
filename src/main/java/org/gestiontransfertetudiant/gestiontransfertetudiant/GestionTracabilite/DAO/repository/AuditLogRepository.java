package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionTracabilite.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionTracabilite.DAO.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
    Page<AuditLog> findByUtilisateurLogin(String login, Pageable pageable);
    Page<AuditLog> findByActionContaining(String action, Pageable pageable);

    @Query("SELECT a FROM AuditLog a WHERE a.dateAction BETWEEN :debut AND :fin")
    Page<AuditLog> findByDateRange(@Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin, Pageable pageable);

    long countByAction(String action);
}