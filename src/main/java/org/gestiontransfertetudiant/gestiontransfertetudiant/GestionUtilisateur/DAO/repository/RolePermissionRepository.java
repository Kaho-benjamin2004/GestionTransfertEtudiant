package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.RolePermission;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {
    List<RolePermission> findByRoleId(UUID roleId);

    @Query("SELECT rp.permission.nom FROM RolePermission rp WHERE rp.role.id = :roleId")
    List<String> findPermissionNomsByRoleId(@Param("roleId") UUID roleId);
}