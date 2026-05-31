package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto.JournalFraudeResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity.JournalFraude;

import java.time.LocalDateTime;
import java.util.UUID;

public class JournalFraudeMapper {

    public static JournalFraudeResponseDTO toDTO(JournalFraude journal) {
        if (journal == null) return null;
        return JournalFraudeResponseDTO.builder()
                .id(journal.getId())
                .dateExecution(journal.getDateExecution())
                .action(journal.getAction())
                .nbAnomaliesDetectees(journal.getNbAnomaliesDetectees())
                .details(journal.getDetails())
                .executeurId(journal.getExecuteurId())
                .build();
    }

    public static JournalFraude toEntity(String action, int nbAnomaliesDetectees, String details, UUID executeurId) {
        JournalFraude journal = new JournalFraude();
        journal.setDateExecution(LocalDateTime.now());
        journal.setAction(action);
        journal.setNbAnomaliesDetectees(nbAnomaliesDetectees);
        journal.setDetails(details);
        journal.setExecuteurId(executeurId);
        return journal;
    }
}