package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.Repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity.Anomalie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AnomalieRepository extends JpaRepository<Anomalie, UUID> {
    long countByResolueTrue();
    long countByNiveau(String niveau);
    

    @Query("SELECT a FROM Anomalie a WHERE a.entiteConcerneeId = :entiteId AND a.entiteType = :entiteType")
    Page<Anomalie> findByEntite(@Param("entiteId") UUID entiteId, @Param("entiteType") String entiteType, Pageable pageable);
}