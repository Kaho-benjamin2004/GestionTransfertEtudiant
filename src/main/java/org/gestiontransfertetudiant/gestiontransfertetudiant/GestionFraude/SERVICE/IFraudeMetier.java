package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.SERVICE;


import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto.*;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.BusinessException;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IFraudeMetier {
    double getTauxFraude();

    // ========== Gestion des règles de détection ==========
    RegleDetectionResponseDTO creerRegle(RegleDetectionRequestDTO request) throws BusinessException;
    RegleDetectionResponseDTO modifierRegle(UUID id, RegleDetectionRequestDTO request) throws ResourceNotFoundException;
    void supprimerRegle(UUID id) throws ResourceNotFoundException;
    Page<RegleDetectionResponseDTO> listerRegles(Pageable pageable);
    RegleDetectionResponseDTO getRegle(UUID id) throws ResourceNotFoundException;

    // ========== Détection automatique (périodique) ==========
    DetectionResultDTO declencherVerificationManuelle(String entiteType, UUID entiteId) throws BusinessException;
    void verificationsPeriodiques(); // appelé par @Scheduled

    // ========== Gestion des anomalies ==========
    AnomalieResponseDTO signalerAnomalie(AnomalieRequestDTO request) throws BusinessException;
    void resoudreAnomalie(UUID anomalieId, String commentaireResolution) throws ResourceNotFoundException, BusinessException;
    Page<AnomalieResponseDTO> listerAnomalies(Pageable pageable);
    AnomalieResponseDTO getAnomalie(UUID id) throws ResourceNotFoundException;

    // ========== Journalisation et statistiques ==========
    Page<JournalFraudeResponseDTO> consulterJournal(Pageable pageable);
    StatistiqueFraudeDTO getStatistiques();
}