package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.request.DocumentRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.response.DocumentResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity.Document;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class DocumentMapper {

    public static DocumentResponseDTO toDTO(Document document) {
        if (document == null) return null;
        return DocumentResponseDTO.builder()
                .id(document.getId())
                .nom(document.getNom())
                .type(document.getType())
                .cheminStockage(document.getCheminStockage())
                .hash(document.getHash())
                .dateDepot(document.getDateDepot())
                .statut(document.getStatut())
                .versions(document.getVersions() != null ?
                        document.getVersions().stream()
                        .map(VersionDocumentMapper::toDTO)
                        .collect(Collectors.toList()) : null)
                .verifications(document.getVerifications() != null ?
                        document.getVerifications().stream()
                        .map(VerificationMapper::toDTO)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public static Document toEntity(DocumentRequestDTO requestDTO, String cheminStockage, String hash) {
        if (requestDTO == null) return null;
        Document document = new Document();
        document.setNom(requestDTO.getFichier().getOriginalFilename());
        document.setType(requestDTO.getType());
        document.setCheminStockage(cheminStockage);
        document.setHash(hash);
        document.setDateDepot(LocalDateTime.now());
        document.setStatut("EN_ATTENTE");
        return document;
    }
}