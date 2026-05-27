package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.RolePermission;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.RolePermissionId;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.PermissionRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.RolePermissionRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.RoleRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Transactional
    public void assignPermissionToRole(UUID roleId, UUID permissionId) {
        var role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role", roleId));
        var perm = permissionRepository.findById(permissionId).orElseThrow(() -> new ResourceNotFoundException("Permission", permissionId));
        RolePermission rp = RolePermission.builder()
                .id(new RolePermissionId(roleId, permissionId))
                .role(role)
                .permission(perm)
                .build();
        rolePermissionRepository.save(rp);
    }

    @Transactional
    public void removePermissionFromRole(UUID roleId, UUID permissionId) {
        rolePermissionRepository.deleteById(new RolePermissionId(roleId, permissionId));
    }
}