package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.Repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity.Alerte;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity.RegleDetection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AlerteRepository extends JpaRepository<Alerte, UUID> {
    List<Alerte> findByTraiteeFalse();
    Page<Alerte> findByTraiteeFalseOrderByDateCreationDesc(Pageable pageable);
}