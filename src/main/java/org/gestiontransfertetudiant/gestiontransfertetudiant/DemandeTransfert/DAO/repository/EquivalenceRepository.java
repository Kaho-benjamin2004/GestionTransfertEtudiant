package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EquivalenceRepository extends JpaRepository<Equivalence, UUID> {
    Optional<Equivalence> findByDemandeId(UUID demandeId);
}

