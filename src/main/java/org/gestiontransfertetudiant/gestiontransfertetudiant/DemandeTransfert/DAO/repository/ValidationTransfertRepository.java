package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ValidationTransfertRepository extends JpaRepository<Validation, UUID> {
    // ... autres méthodes
    long countByStatut(String statut);
}