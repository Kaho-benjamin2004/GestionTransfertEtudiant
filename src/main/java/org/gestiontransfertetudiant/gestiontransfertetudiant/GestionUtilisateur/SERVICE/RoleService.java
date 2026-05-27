package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.mapper.PermissionMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.mapper.RoleMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.RoleRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.PermissionResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.RoleDetailResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.RoleResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Role;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.RolePermissionRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.RoleRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.AlreadyExistsException;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RolePermissionService rolePermissionService;
    private final RolePermissionRepository rolePermissionRepository;

    @Transactional
    public RoleResponseDTO createRole(RoleRequestDTO request) {
        if (roleRepository.findByNom(request.getNom()).isPresent()) {
            throw new AlreadyExistsException("Rôle déjà existant");
        }
        Role role = RoleMapper.toEntity(request);
        role = roleRepository.save(role);
        if (request.getPermissionIds() != null && !request.getPermissionIds().isEmpty()) {
            Role finalRole = role;
            request.getPermissionIds().forEach(permId -> rolePermissionService.assignPermissionToRole(finalRole.getId(), permId));
        }
        return RoleMapper.toDTO(role);
    }

    @Transactional(readOnly = true)
    public RoleDetailResponseDTO getRoleById(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", id));
        return RoleMapper.toDetailDTO(role);
    }

    @Transactional(readOnly = true)
    public List<RoleResponseDTO> getAllRoles() {
        return roleRepository.findAll().stream().map(RoleMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public RoleResponseDTO updateRole(UUID id, RoleRequestDTO request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", id));
        role.setNom(request.getNom());
        role.setDescription(request.getDescription());
        role = roleRepository.save(role);
        return RoleMapper.toDTO(role);
    }

    @Transactional
    public void deleteRole(UUID id) {
        if (!roleRepository.existsById(id)) throw new ResourceNotFoundException("Role", id);
        roleRepository.deleteById(id);
    }

    // Utilisation de RolePermissionRepository
    public List<String> getPermissionNamesForRole(UUID roleId) {
        return rolePermissionRepository.findPermissionNomsByRoleId(roleId);
    }

    public List<PermissionResponseDTO> getPermissionsForRole(UUID roleId) {
        return rolePermissionRepository.findByRoleId(roleId).stream()
                .map(rp -> PermissionMapper.toDTO(rp.getPermission()))
                .collect(Collectors.toList());
    }
}