package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity.CoursEquivalent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CoursEquivalentRepository extends JpaRepository<CoursEquivalent, UUID> {
    List<CoursEquivalent> findByEquivalenceId(UUID equivalenceId);
}
