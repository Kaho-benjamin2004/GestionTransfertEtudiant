package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.mapper.PermissionMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.PermissionRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.PermissionResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Permission;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.PermissionRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.AlreadyExistsException;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionResponseDTO createPermission(PermissionRequestDTO request) {
        if (permissionRepository.findByRessourceAndAction(request.getRessource(), request.getAction()).isPresent())
            throw new AlreadyExistsException("Permission déjà existante");
        Permission permission = PermissionMapper.toEntity(request);
        permission = permissionRepository.save(permission);
        return PermissionMapper.toDTO(permission);
    }

    public List<PermissionResponseDTO> getAllPermissions() {
        return permissionRepository.findAll().stream().map(PermissionMapper::toDTO).collect(Collectors.toList());
    }

    public PermissionResponseDTO getPermission(UUID id) {
        Permission p = permissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Permission", id));
        return PermissionMapper.toDTO(p);
    }

    public void deletePermission(UUID id) {
        permissionRepository.deleteById(id);
    }
}