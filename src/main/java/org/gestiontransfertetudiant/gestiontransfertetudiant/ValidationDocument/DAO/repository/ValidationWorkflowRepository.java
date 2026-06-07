package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.repository;

import jakarta.validation.constraints.NotBlank;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.Validation;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ValidationWorkflowRepository extends JpaRepository<Workflow, UUID> {

    @Query("SELECT v FROM Validation v WHERE v.entiteId = :entiteId AND v.entiteType = :entiteType")
    List<Validation> findByEntiteIdAndEntiteType(@Param("entiteId") UUID entiteId, @Param("entiteType") String entiteType);



    Optional<Object> findByNom(@NotBlank(message = "Le nom du workflow est obligatoire") String nom);
}