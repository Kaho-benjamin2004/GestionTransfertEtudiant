package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.EtablissementAnterieur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface EtablissementAnterieurRepository extends JpaRepository<EtablissementAnterieur, UUID> {
    List<EtablissementAnterieur> findByEtudiantId(UUID etudiantId);
}