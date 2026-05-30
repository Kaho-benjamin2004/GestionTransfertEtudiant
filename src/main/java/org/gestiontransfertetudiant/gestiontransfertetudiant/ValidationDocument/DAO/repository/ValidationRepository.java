package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ValidationRepository extends JpaRepository<Validation, UUID> {
    List<Validation> findByEntiteIdAndEntiteType(UUID entiteId, String entiteType);
    Optional<Validation> findFirstByEntiteIdAndEntiteTypeOrderByDateSoumissionDesc(UUID entiteId, String entiteType);
}
