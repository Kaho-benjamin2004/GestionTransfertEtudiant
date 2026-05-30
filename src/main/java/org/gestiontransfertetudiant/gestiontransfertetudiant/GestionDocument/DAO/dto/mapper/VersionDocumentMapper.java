package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.response.VersionDocumentResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity.Document;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity.VersionDocument;

import java.time.LocalDateTime;

public class VersionDocumentMapper {

    public static VersionDocumentResponseDTO toDTO(VersionDocument version) {
        if (version == null) return null;
        return VersionDocumentResponseDTO.builder()
                .id(version.getId())
                .version(version.getVersion())
                .cheminStockage(version.getCheminStockage())
                .hash(version.getHash())
                .dateModification(version.getDateModification())
                .build();
    }

    public static VersionDocument toEntity(Document document, int versionNumber, String cheminStockage, String hash) {
        if (document == null) return null;
        VersionDocument version = new VersionDocument();
        version.setVersion(versionNumber);
        version.setCheminStockage(cheminStockage);
        version.setHash(hash);
        version.setDateModification(LocalDateTime.now());
        version.setDocument(document);
        return version;
    }
}