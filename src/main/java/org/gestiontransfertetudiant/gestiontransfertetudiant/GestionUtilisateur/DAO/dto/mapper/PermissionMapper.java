package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.PermissionRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.PermissionResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Permission;

public class PermissionMapper {

    public static PermissionResponseDTO toDTO(Permission permission) {
        if (permission == null) return null;
        return PermissionResponseDTO.builder()
                .id(permission.getId())
                .nom(permission.getNom())
                .ressource(permission.getRessource())
                .action(permission.getAction())
                .build();
    }

    public static Permission toEntity(PermissionRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        Permission permission = new Permission();
        permission.setNom(requestDTO.getNom());
        permission.setRessource(requestDTO.getRessource());
        permission.setAction(requestDTO.getAction());
        return permission;
    }
}