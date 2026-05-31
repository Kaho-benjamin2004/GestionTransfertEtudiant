package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.HistoriqueValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HistoriqueValidationRepository extends JpaRepository<HistoriqueValidation, UUID> {

    List<HistoriqueValidation> findByValidation_IdOrderByDateActionAsc(UUID validationId);

}