package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findByRessourceAndAction(String ressource, String action);
}