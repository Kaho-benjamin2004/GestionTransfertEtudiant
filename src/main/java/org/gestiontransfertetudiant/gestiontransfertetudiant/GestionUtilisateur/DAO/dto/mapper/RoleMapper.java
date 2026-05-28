package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.RoleRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.RoleDetailResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.RoleResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Role;

import java.util.stream.Collectors;

public class RoleMapper {

    public static RoleResponseDTO toDTO(Role role) {
        if (role == null) return null;
        return RoleResponseDTO.builder()
                .id(role.getId())
                .nom(role.getNom())
                .description(role.getDescription())
                .build();
    }

    public static Role toEntity(RoleRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        Role role = new Role();
        role.setNom(requestDTO.getNom());
        role.setDescription(requestDTO.getDescription());
        return role;
    }

    public static RoleDetailResponseDTO toDetailDTO(Role role) {
        if (role == null) return null;
        return RoleDetailResponseDTO.builder()
                .id(role.getId())
                .nom(role.getNom())
                .description(role.getDescription())
                .nbUtilisateurs(role.getUtilisateurRoles() != null ? role.getUtilisateurRoles().size() : 0)
                .permissions(role.getRolePermissions() != null ?
                        role.getRolePermissions().stream()
                        .map(rp -> PermissionMapper.toDTO(rp.getPermission()))
                        .collect(Collectors.toList()) : null)
                .build();
    }
}