package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.SERVICE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.Repository.DocumentRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.Repository.VerificationRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.mapper.DocumentMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.mapper.VerificationMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.request.DocumentRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.response.DocumentResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity.Document;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity.Verification;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.BusinessException;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentMetierImpl implements IDocumentMetier {
//    private final ValidationWorkflowRepository validationRepository;
    private final DocumentRepository documentRepository;
    private final VerificationRepository verificationRepository;
    private Pageable pageable;

    @Override
    public DocumentResponseDTO deposerDocument(DocumentRequestDTO request, UUID proprietaireId) throws BusinessException {
        String hash = "hash_simule_" + System.currentTimeMillis();
        String chemin = "/documents/" + proprietaireId + "/" + request.getFichier().getOriginalFilename();
        Document document = DocumentMapper.toEntity(request, chemin, hash, proprietaireId);
        document = documentRepository.save(document);
        return DocumentMapper.toDTO(document);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponseDTO consulterDocument(UUID documentId) throws BusinessException {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", documentId));
        return DocumentMapper.toDTO(document);
    }

    @Override
    @Transactional(readOnly = true)

    public Page<DocumentResponseDTO> listerDocumentsParProprietaire(UUID proprietaireId, PageRequest pageRequest) {
        return documentRepository.findByProprietaireId(proprietaireId, pageable).map(DocumentMapper::toDTO);
    }
    @Override
    public void verifierAuthenticite(UUID documentId, UUID verificateurId) throws BusinessException {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", documentId));
        // Simulation de vérification d'intégrité (comparaison du hash stocké avec un hash recalculé)
        boolean resultat = true; // à implémenter
        Verification verification = VerificationMapper.toEntity(document, resultat, "Vérification automatique", verificateurId);
        verificationRepository.save(verification);
        log.info("Authenticité vérifiée pour document {} par {}", documentId, verificateurId);
    }

    @Override
    public void signerElectroniquement(UUID documentId, String signature) throws BusinessException {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", documentId));
        document.setSignature(signature);
        documentRepository.save(document);
    }

    @Override
    public void archiverDocument(UUID documentId) throws BusinessException {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", documentId));
        document.setStatut("ARCHIVE");
        documentRepository.save(document);
    }

    @Override
    public void detecterFalsification(UUID id) {

    }

    @Override
    public Object historiqueVersions(UUID id) {
        return null;
    }
}