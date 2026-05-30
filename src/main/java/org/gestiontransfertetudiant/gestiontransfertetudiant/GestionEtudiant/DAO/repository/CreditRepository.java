package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CreditRepository extends JpaRepository<Credit, UUID> {
    List<Credit> findByParcoursAcademiqueId(UUID parcoursAcademiqueId);

    @Query("SELECT c FROM Credit c WHERE c.parcoursAcademique.etudiant.id = :etudiantId")
    List<Credit> findByEtudiantId(@Param("etudiantId") UUID etudiantId);
}