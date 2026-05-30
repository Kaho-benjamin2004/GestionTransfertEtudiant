package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity.PieceJustificative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PieceJustificativeRepository extends JpaRepository<PieceJustificative, UUID> {
    List<PieceJustificative> findByDemandeId(UUID demandeId);
}
