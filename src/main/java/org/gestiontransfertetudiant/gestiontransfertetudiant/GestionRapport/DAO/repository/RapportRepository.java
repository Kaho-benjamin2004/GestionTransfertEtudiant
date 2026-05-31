package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.DAO.entity.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RapportRepository extends JpaRepository<Rapport, UUID> {
}
