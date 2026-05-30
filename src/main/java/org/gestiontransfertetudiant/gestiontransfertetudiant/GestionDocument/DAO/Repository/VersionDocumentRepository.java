package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.Repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity.VersionDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VersionDocumentRepository extends JpaRepository<VersionDocument, UUID> {
    List<VersionDocument> findByDocumentIdOrderByVersionDesc(UUID documentId);
}
