package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.Repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity.JournalFraude;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface JournalFraudeRepository extends JpaRepository<JournalFraude, UUID> {
    Page<JournalFraude> findByAction(String action, Pageable pageable);

    @Query("SELECT j FROM JournalFraude j WHERE j.dateExecution BETWEEN :debut AND :fin")
    Page<JournalFraude> findByDateRange(@Param("debut") LocalDateTime debut, @Param("fin") LocalDateTime fin, Pageable pageable);
}
