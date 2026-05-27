package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    Optional<Session> findByToken(String token);

    @Modifying
    @Transactional
    @Query("UPDATE Session s SET s.actif = false, s.dateFin = :now WHERE s.token = :token AND s.actif = true")
    int invaliderSession(@Param("token") String token, @Param("now") LocalDateTime now);
    @Query("SELECT s FROM Session s WHERE s.utilisateur.id = :userId AND s.actif = true")

    @Modifying
    @Transactional

//    @Query("DELETE FROM Session s WHERE s.dateFin < :date OR (s.actif = false AND s.dateFin IS NOT NULL)")
    int nettoyerSessionsExpirees(@Param("date") LocalDateTime date);


    @Query("SELECT s FROM Session s WHERE s.utilisateur.id = :userId AND s.actif = true")
    List<Session> findByUtilisateurIdAndActifTrue(@Param("userId") UUID userId);
}