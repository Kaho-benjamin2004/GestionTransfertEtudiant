package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.ParcoursAcademique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParcoursAcademiqueRepository extends JpaRepository<ParcoursAcademique, UUID> {
    Optional<ParcoursAcademique> findFirstByEtudiantIdOrderByAnneeUniversitaireDesc(UUID etudiantId);

    List<ParcoursAcademique> findByEtudiantId(UUID etudiantId);
}
