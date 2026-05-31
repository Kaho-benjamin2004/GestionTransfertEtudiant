package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.SERVICE;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.request.DocumentRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.response.DocumentResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

//public interface IDocumentMetier {
//    DocumentResponseDTO deposerDocument(DocumentRequestDTO request, UUID proprietaireId) throws BusinessException;
//    DocumentResponseDTO consulterDocument(UUID documentId) throws ResourceNotFoundException;
//    Page<DocumentResponseDTO> listerDocumentsParProprietaire(UUID proprietaireId, Pageable pageable);
//    void verifierAuthenticite(UUID documentId, UUID verificateurId) throws BusinessException;
//    void signerElectroniquement(UUID documentId, String signature) throws ResourceNotFoundException;
//    void archiverDocument(UUID documentId) throws ResourceNotFoundException;
//    List<VersionDocumentResponseDTO> historiqueVersions(UUID documentId) throws ResourceNotFoundException;
//    void detecterFalsification(UUID documentId) throws BusinessException;
//}


public interface IDocumentMetier {
    DocumentResponseDTO deposerDocument(DocumentRequestDTO request, UUID proprietaireId) throws BusinessException;
    DocumentResponseDTO consulterDocument(UUID documentId) throws BusinessException;
    Page<DocumentResponseDTO> listerDocumentsParProprietaire(UUID proprietaireId, PageRequest pageRequest);
    void verifierAuthenticite(UUID documentId, UUID verificateurId) throws BusinessException;
    void signerElectroniquement(UUID documentId, String signature) throws BusinessException;
    void archiverDocument(UUID documentId) throws BusinessException;


    void detecterFalsification(UUID id);

    Object historiqueVersions(UUID id);
}