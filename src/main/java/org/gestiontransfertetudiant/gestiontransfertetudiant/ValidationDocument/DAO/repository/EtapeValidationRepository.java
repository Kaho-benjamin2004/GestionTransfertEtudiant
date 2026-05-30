package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.EtapeValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EtapeValidationRepository extends JpaRepository<EtapeValidation, UUID> {
    List<EtapeValidation> findByWorkflowIdOrderByOrdreAsc(UUID workflowId);
}
