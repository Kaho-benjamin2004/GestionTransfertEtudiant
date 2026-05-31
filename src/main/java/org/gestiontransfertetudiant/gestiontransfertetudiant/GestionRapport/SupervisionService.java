package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.repository.DemandeTransfertRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SupervisionService {
    private final DemandeTransfertRepository demandeRepository;
    private final UtilisateurRepository utilisateurRepository;

    public Map<String, Object> getTableauBord() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalTransferts", demandeRepository.count());
        stats.put("transfertsEnCours", demandeRepository.countByStatut("EN_COURS"));
        stats.put("totalUtilisateurs", utilisateurRepository.count());
        // ...
        return stats;
    }
}