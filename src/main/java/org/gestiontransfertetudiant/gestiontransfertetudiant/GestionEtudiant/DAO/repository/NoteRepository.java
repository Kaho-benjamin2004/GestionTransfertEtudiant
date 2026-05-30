package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {
    List<Note> findByParcoursAcademiqueId(UUID parcoursAcademiqueId);

    @Query("SELECT n FROM Note n WHERE n.parcoursAcademique.etudiant.id = :etudiantId")
    List<Note> findByEtudiantId(@Param("etudiantId") UUID etudiantId);
}
