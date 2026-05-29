package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.ParcoursAcademique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParcoursAcademiqueRepository extends JpaRepository<ParcoursAcademique, UUID> {
    List<ParcoursAcademique> findByEtudiantId(UUID etudiantId);
    Optional<ParcoursAcademique> findFirstByEtudiantIdOrderByAnneeUniversitaireDesc(UUID etudiantId);

    @Query("SELECT p FROM ParcoursAcademique p LEFT JOIN FETCH p.notes LEFT JOIN FETCH p.credits WHERE p.etudiant.id = :etudiantId")
    List<ParcoursAcademique> findByEtudiantIdWithNotesAndCredits(@Param("etudiantId") UUID etudiantId);
}