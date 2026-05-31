package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.SERVICE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.repository.DemandeTransfertRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.Repository.AnomalieRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.Repository.JournalFraudeRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.Repository.RegleDetectionRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto.*;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity.Anomalie;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity.JournalFraude;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity.RegleDetection;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.mapper.AnomalieMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.mapper.JournalFraudeMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.mapper.RegleDetectionMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.BusinessException;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FraudeMetierImpl implements IFraudeMetier {

    private final RegleDetectionRepository regleRepository;
    private final AnomalieRepository anomalieRepository;
    private final JournalFraudeRepository journalRepository;
    private final DemandeTransfertRepository demandeTransfertRepository;



    @Override
    public double getTauxFraude() {
        long totalAnomalies = anomalieRepository.count();
        long totalDemandes = demandeTransfertRepository.count();
        if (totalDemandes == 0) return 0.0;
        return (double) totalAnomalies / totalDemandes * 100;
    }

//    @Override
//    @Transactional(readOnly = true)
//    public double getTauxFraude() {
//        long totalAnomalies = anomalieRepository.count();
//        long totalDemandes = demandeTransfertRepository.count();
//        if (totalDemandes == 0) {
//            return 0.0;
//        }
//        return (double) totalAnomalies / totalDemandes * 100;
//    }
    // ========== Gestion des règles ==========
    @Override
    public RegleDetectionResponseDTO creerRegle(RegleDetectionRequestDTO request) throws BusinessException {
        if (regleRepository.findByNom(request.getNom()).isPresent()) {
            throw new BusinessException("Une règle avec ce nom existe déjà", "DUPLICATE_RULE");
        }
        RegleDetection regle = RegleDetectionMapper.toEntity(request);
        regle = regleRepository.save(regle);
        return RegleDetectionMapper.toDTO(regle);
    }

    @Override
    public RegleDetectionResponseDTO modifierRegle(UUID id, RegleDetectionRequestDTO request) throws ResourceNotFoundException {
        RegleDetection regle = regleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Règle de détection", id));
        regle.setNom(request.getNom());
        regle.setDescription(request.getDescription());
        regle.setType(request.getType());
//        regle.setExpression(request.getExpression());
        if (request.getActif() != null) regle.setActif(request.getActif());
        regle = regleRepository.save(regle);
        return RegleDetectionMapper.toDTO(regle);
    }

    @Override
    public void supprimerRegle(UUID id) throws ResourceNotFoundException {
        if (!regleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Règle de détection", id);
        }
        regleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RegleDetectionResponseDTO> listerRegles(Pageable pageable) {
        return regleRepository.findAll(pageable).map(RegleDetectionMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public RegleDetectionResponseDTO getRegle(UUID id) throws ResourceNotFoundException {
        RegleDetection regle = regleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Règle de détection", id));
        return RegleDetectionMapper.toDTO(regle);
    }

    // ========== Détection ==========
    @Override
    public DetectionResultDTO declencherVerificationManuelle(String entiteType, UUID entiteId) throws BusinessException {
        log.info("Déclenchement manuel de la vérification pour entité {} : {}", entiteType, entiteId);
        List<String> anomaliesTrouvees = new ArrayList<>();
        // Appliquer les règles actives (simulation)
        List<RegleDetection> regles = regleRepository.findByActifTrue();
        for (RegleDetection regle : regles) {
            if (appliquerRegle(regle, entiteType, entiteId)) {
                anomaliesTrouvees.add(regle.getNom() + " : " + regle.getDescription());
            }
        }
        // Création automatique des anomalies en base
        for (String msg : anomaliesTrouvees) {
            AnomalieRequestDTO anomalieReq = new AnomalieRequestDTO();
            anomalieReq.setTypeAnomalie("AUTOMATIQUE");
            anomalieReq.setNiveau("AVERTISSEMENT");
            anomalieReq.setMessage(msg);
            anomalieReq.setEntiteConcerneeId(entiteId);
            anomalieReq.setEntiteType(entiteType);
            signalerAnomalie(anomalieReq);
        }
        // Journalisation
        journaliser("DETECTION_MANUELLE", anomaliesTrouvees.size(), "Entité " + entiteType + "/" + entiteId);
        return DetectionResultDTO.builder()
                .fraudeDetectee(!anomaliesTrouvees.isEmpty())
                .anomalies(anomaliesTrouvees)
                .recommandation(anomaliesTrouvees.isEmpty() ? "Aucune anomalie" : "Vérifier les anomalies signalées")
                .build();
    }

    @Override
    @Scheduled(cron = "0 0 2 * * *") // tous les jours à 2h du matin
    public void verificationsPeriodiques() {
        log.info("Démarrage de la vérification périodique des fraudes");
        int totalAnomalies = 0;
        List<String> details = new ArrayList<>();
        // Exemple : vérifier les doublons de demandes de transfert
        try {
            // Appeler un service de détection spécifique (simulation)
            totalAnomalies += 5; // résultat fictif
            details.add("Doublons détectés : 5");
        } catch (Exception e) {
            log.error("Erreur lors de la vérification périodique", e);
        }
        journaliser("VERIFICATION_PERIODIQUE", totalAnomalies, String.join("; ", details));
        log.info("Vérification périodique terminée. {} anomalie(s) détectée(s).", totalAnomalies);
    }

    private boolean appliquerRegle(RegleDetection regle, String entiteType, UUID entiteId) {
        // Ici, la logique d'évaluation de l'expression (ex: SpEL)
        // Pour l'exemple, on retourne true si le type d'entité correspond à "DEMANDE_TRANSFERT"
        return "DEMANDE_TRANSFERT".equals(entiteType);
    }

    private void journaliser(String action, int nbAnomalies, String details) {
        JournalFraude journal = JournalFraudeMapper.toEntity(action, nbAnomalies, details, null); // null pour système
        journalRepository.save(journal);
    }

    // ========== Gestion des anomalies ==========
    @Override
    public AnomalieResponseDTO signalerAnomalie(AnomalieRequestDTO request) throws BusinessException {
        Anomalie anomalie = AnomalieMapper.toEntity(request);
        anomalie = anomalieRepository.save(anomalie);
        return AnomalieMapper.toDTO(anomalie);
    }

    @Override
    public void resoudreAnomalie(UUID anomalieId, String commentaireResolution) throws ResourceNotFoundException, BusinessException {
        Anomalie anomalie = anomalieRepository.findById(anomalieId)
                .orElseThrow(() -> new ResourceNotFoundException("Anomalie", anomalieId));
        if (anomalie.getResolue()) {
            throw new BusinessException("Cette anomalie est déjà résolue", "ALREADY_RESOLVED");
        }
        anomalie.setResolue(true);
        anomalie.setDateResolution(LocalDateTime.now());
        anomalie.setCommentaireResolution(commentaireResolution);
        anomalieRepository.save(anomalie);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnomalieResponseDTO> listerAnomalies(Pageable pageable) {
        return anomalieRepository.findAll(pageable).map(AnomalieMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public AnomalieResponseDTO getAnomalie(UUID id) throws ResourceNotFoundException {
        Anomalie anomalie = anomalieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anomalie", id));
        return AnomalieMapper.toDTO(anomalie);
    }

    // ========== Journalisation et statistiques ==========
    @Override
    @Transactional(readOnly = true)
    public Page<JournalFraudeResponseDTO> consulterJournal(Pageable pageable) {
        return journalRepository.findAll(pageable).map(JournalFraudeMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public StatistiqueFraudeDTO getStatistiques() {
        long total = anomalieRepository.count();
        long resolues = anomalieRepository.countByResolueTrue();
        long critiques = anomalieRepository.countByNiveau("CRITIQUE");
        // Statistiques par type et par entité (simulées)
        HashMap<String, Long> parType = new HashMap<>();
        parType.put("DOUBLON", 10L);
        parType.put("INCOHERENCE", 5L);
        HashMap<String, Long> parEntite = new HashMap<>();
        parEntite.put("DEMANDE_TRANSFERT", 12L);
        parEntite.put("DOCUMENT", 3L);
        return StatistiqueFraudeDTO.builder()
                .totalAnomalies(total)
                .anomaliesResolues(resolues)
                .anomaliesCritiques(critiques)
                .anomaliesParType(parType)
                .anomaliesParEntite(parEntite)
                .build();
    }
}