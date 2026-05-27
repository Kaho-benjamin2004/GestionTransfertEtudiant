package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.JournalConnexion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JournalConnexionRepository extends JpaRepository<JournalConnexion, UUID> {
    // Dans JournalConnexionRepository
    Page<JournalConnexion> findByUtilisateurId(UUID utilisateurId, Pageable pageable);
}