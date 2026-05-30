package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.Repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity.Document;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity.Verification;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity.VersionDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
    List<Document> findByStatut(String statut);
    Optional<Document> findByHash(String hash);
}

