package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.EtapeValidation;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.HistoriqueValidation;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.Validation;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkflowRepository extends JpaRepository<Workflow, UUID> {
    Optional<Workflow> findByNom(String nom);
}

