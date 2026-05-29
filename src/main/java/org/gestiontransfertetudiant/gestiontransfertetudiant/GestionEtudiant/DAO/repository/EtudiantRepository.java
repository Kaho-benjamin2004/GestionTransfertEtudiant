package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.Etudiant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface EtudiantRepository extends JpaRepository<Etudiant, UUID> {
    Optional<Etudiant> findByNumeroEtudiant(String numeroEtudiant);
    Optional<Etudiant> findByUtilisateurId(UUID utilisateurId);
    @Query("SELECT e FROM Etudiant e WHERE e.utilisateur.profil.nom LIKE %:critere% OR e.utilisateur.profil.prenom LIKE %:critere% OR e.numeroEtudiant LIKE %:critere% OR e.utilisateur.login LIKE %:critere%")
    Page<Etudiant> findByNomContainingOrPrenomContainingOrNumeroEtudiantContainingOrLoginContaining(String nom, String prenom, String numero, String login, Pageable pageable);

    @Query("SELECT e FROM Etudiant e LEFT JOIN FETCH e.parcoursAcademiques WHERE e.id = :id")
    Optional<Etudiant> findByIdWithParcours(@Param("id") UUID id);

        @Query("SELECT e FROM Etudiant e WHERE " +
                "LOWER(e.utilisateur.profil.nom) LIKE LOWER(CONCAT('%', :critere, '%')) OR " +
                "LOWER(e.utilisateur.profil.prenom) LIKE LOWER(CONCAT('%', :critere, '%')) OR " +
                "LOWER(e.numeroEtudiant) LIKE LOWER(CONCAT('%', :critere, '%')) OR " +
                "LOWER(e.utilisateur.login) LIKE LOWER(CONCAT('%', :critere, '%'))")
        Page<Etudiant> rechercherParCritere(@Param("critere") String critere, Pageable pageable);

        /**
         * Compte le nombre d'étudiants pour une filière et une année universitaire.
         */
        @Query("SELECT COUNT(e) FROM Etudiant e WHERE " +
                "e.parcoursActuel = :filiere AND " +
                "EXISTS (SELECT p FROM ParcoursAcademique p WHERE p.etudiant = e AND p.anneeUniversitaire = :annee)")
        Integer countByFiliereAndAnnee(@Param("filiere") String filiere, @Param("annee") String anneeUniversitaire);

        /**
         * Calcule la moyenne générale moyenne des étudiants pour une filière et une année universitaire.
         */
        @Query("SELECT AVG(p.moyenne) FROM ParcoursAcademique p " +
                "WHERE p.etudiant.parcoursActuel = :filiere AND p.anneeUniversitaire = :annee")
        BigDecimal averageMoyenneByFiliereAndAnnee(@Param("filiere") String filiere, @Param("annee") String annee);
    }
