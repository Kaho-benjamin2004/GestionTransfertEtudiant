package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity;

import jakarta.persistence.*;
import lombok.*;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Permission;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Role;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "role_permission",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "permission_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermission {

    @EmbeddedId
    private RolePermissionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;
}

