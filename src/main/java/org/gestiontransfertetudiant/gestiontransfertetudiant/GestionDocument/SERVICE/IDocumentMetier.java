package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.SERVICE;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.request.DocumentRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.response.DocumentResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.response.VersionDocumentResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.BusinessException;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

public interface IDocumentMetier {
    DocumentResponseDTO deposerDocument(DocumentRequestDTO request, UUID proprietaireId) throws BusinessException;
    DocumentResponseDTO consulterDocument(UUID documentId) throws ResourceNotFoundException;
    List<DocumentResponseDTO> listerDocumentsParProprietaire(UUID proprietaireId);
    void verifierAuthenticite(UUID documentId, UUID verificateurId) throws BusinessException;
    void signerElectroniquement(UUID documentId, String signature) throws ResourceNotFoundException;
    void archiverDocument(UUID documentId) throws ResourceNotFoundException;
    List<VersionDocumentResponseDTO> historiqueVersions(UUID documentId) throws ResourceNotFoundException;
    void detecterFalsification(UUID documentId) throws BusinessException;
}