package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.UniteEnseignement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UniteEnseignementRepository extends JpaRepository<UniteEnseignement, UUID> {
    Optional<UniteEnseignement> findByCode(String code);
}