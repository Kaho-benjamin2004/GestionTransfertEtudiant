package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.SERVICE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.Repository.DocumentRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.Repository.VerificationRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.Repository.VersionDocumentRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.mapper.DocumentMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.mapper.VerificationMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.mapper.VersionDocumentMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.request.DocumentRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.response.DocumentResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.response.VersionDocumentResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity.Document;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity.VersionDocument;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity.Verification;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.BusinessException;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentMetierImpl implements IDocumentMetier {

    private final DocumentRepository documentRepository;
    private final VersionDocumentRepository versionDocumentRepository;
    private final VerificationRepository verificationRepository;

    @Value("${document.storage.path:./uploads/documents}")
    private String storagePath;

    @Override
    public DocumentResponseDTO deposerDocument(DocumentRequestDTO request, UUID proprietaireId) throws BusinessException {
        MultipartFile fichier = request.getFichier();
        if (fichier.isEmpty()) {
            throw new BusinessException("Le fichier est vide", "EMPTY_FILE");
        }
        try {
            // 1. Calculer le hash SHA-256 du fichier
            String hash = calculerHash(fichier.getBytes());
            // 2. Stocker le fichier physiquement
            String chemin = stockerFichier(fichier, proprietaireId);
            // 3. Créer l'entité Document
            Document document = DocumentMapper.toEntity(request, chemin, hash);
            document = documentRepository.save(document);
            // 4. Créer une première version (v1)
            VersionDocument version = VersionDocumentMapper.toEntity(document, 1, chemin, hash);
            versionDocumentRepository.save(version);
            // 5. Ajouter une vérification automatique d'intégrité (optionnelle)
            return DocumentMapper.toDTO(document);
        } catch (IOException | NoSuchAlgorithmException e) {
            log.error("Erreur lors du dépôt du document", e);
            throw new BusinessException("Impossible de traiter le fichier", "FILE_PROCESSING_ERROR");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponseDTO consulterDocument(UUID documentId) throws ResourceNotFoundException {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", documentId));
        return DocumentMapper.toDTO(document);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponseDTO> listerDocumentsParProprietaire(UUID proprietaireId) {
        // Si on lie document à propriétaire, il faudrait un champ dans Document. Pour l'exemple, on suppose un champ proprietaireId.
        // Sinon, on peut ajouter une relation ManyToOne avec Utilisateur. Ici on simule via un repository custom.
        // Pour l'instant, on retourne tous les documents (à adapter).
        return documentRepository.findAll().stream()
                .map(DocumentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void verifierAuthenticite(UUID documentId, UUID verificateurId) throws BusinessException {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", documentId));
        try {
            // Récupérer le fichier physique et recalculer son hash
            Path path = Paths.get(document.getCheminStockage());
            byte[] fileBytes = Files.readAllBytes(path);
            String currentHash = calculerHash(fileBytes);
            boolean integre = currentHash.equals(document.getHash());
            String commentaire = integre ? "Document authentique" : "Falsification détectée";
            Verification verification = VerificationMapper.toEntity(document, integre, commentaire, verificateurId);
            verificationRepository.save(verification);
            if (!integre) {
                throw new BusinessException("Le document a été falsifié !", "FALSIFICATION_DETECTED");
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            log.error("Erreur lors de la vérification du document", e);
            throw new BusinessException("Impossible de vérifier le document", "VERIFICATION_ERROR");
        }
    }

    @Override
    public void signerElectroniquement(UUID documentId, String signature) throws ResourceNotFoundException {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", documentId));
        document.setSignature(signature);
        documentRepository.save(document);
        // Optionnel : enregistrer une vérification supplémentaire
    }

    @Override
    public void archiverDocument(UUID documentId) throws ResourceNotFoundException {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", documentId));
        document.setStatut("ARCHIVE");
        documentRepository.save(document);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VersionDocumentResponseDTO> historiqueVersions(UUID documentId) throws ResourceNotFoundException {
        if (!documentRepository.existsById(documentId)) {
            throw new ResourceNotFoundException("Document", documentId);
        }
        return versionDocumentRepository.findByDocumentIdOrderByVersionDesc(documentId).stream()
                .map(VersionDocumentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void detecterFalsification(UUID documentId) throws BusinessException {
        // Similaire à verifierAuthenticite mais sans enregistrer de vérification
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document", documentId));
        try {
            Path path = Paths.get(document.getCheminStockage());
            byte[] fileBytes = Files.readAllBytes(path);
            String currentHash = calculerHash(fileBytes);
            if (!currentHash.equals(document.getHash())) {
                throw new BusinessException("Falsification détectée : le hash ne correspond pas", "FALSIFICATION");
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new BusinessException("Erreur technique lors de la détection", "TECHNICAL_ERROR");
        }
    }

    // --- Méthodes privées utilitaires ---

    private String calculerHash(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data);
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private String stockerFichier(MultipartFile fichier, UUID proprietaireId) throws IOException {
        // Créer le répertoire de stockage si inexistant
        Path storageDir = Paths.get(storagePath);
        if (!Files.exists(storageDir)) {
            Files.createDirectories(storageDir);
        }
        // Générer un nom unique pour éviter les collisions
        String originalFilename = fichier.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".") ?
                originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String newFileName = System.currentTimeMillis() + "_" + proprietaireId + extension;
        Path targetPath = storageDir.resolve(newFileName);
        Files.copy(fichier.getInputStream(), targetPath);
        return targetPath.toString();
    }
}