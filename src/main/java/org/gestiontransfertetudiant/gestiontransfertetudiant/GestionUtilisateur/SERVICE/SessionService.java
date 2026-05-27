package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.mapper.SessionMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.SessionResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.SessionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    @Scheduled(cron = "0 0 2 * * *")
    public void cleanExpiredSessions() {
        sessionRepository.nettoyerSessionsExpirees(LocalDateTime.now().minusDays(30));
    }

    // Nouvelle méthode
    public List<SessionResponseDTO> getSessionsByUser(UUID userId) {
        return sessionRepository.findByUtilisateurIdAndActifTrue(userId)
                .stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }
}