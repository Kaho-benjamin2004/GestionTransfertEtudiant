package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.mapper.JournalConnexionMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.JournalConnexionResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.JournalConnexionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JournalService {
    private final JournalConnexionRepository journalConnexionRepository;

    public Page<JournalConnexionResponseDTO> getJournauxByUtilisateur(UUID utilisateurId, Pageable pageable) {
        return journalConnexionRepository.findByUtilisateurId(utilisateurId, pageable)
                .map(JournalConnexionMapper::toDTO);
    }
}