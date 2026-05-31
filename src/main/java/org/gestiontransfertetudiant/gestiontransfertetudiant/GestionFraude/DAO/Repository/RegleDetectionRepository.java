package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.Repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity.RegleDetection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RegleDetectionRepository extends JpaRepository<RegleDetection, UUID> {
    List<RegleDetection> findByTypeAndActifTrue(String type);

    // Dans RegleDetectionRepository
    Optional<RegleDetection> findByNom(String nom);

    List<RegleDetection> findByActifTrue();

}