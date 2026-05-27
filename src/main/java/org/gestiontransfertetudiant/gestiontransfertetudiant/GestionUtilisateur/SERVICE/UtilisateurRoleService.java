package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Role;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Utilisateur;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.UtilisateurRole;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.UtilisateurRoleId;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.RoleRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.UtilisateurRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.UtilisateurRoleRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UtilisateurRoleService {
    private final UtilisateurRoleRepository utilisateurRoleRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public void assignRoleToUser(UUID userId, UUID roleId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", userId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", roleId));
        UtilisateurRole ur = UtilisateurRole.builder()
                .id(new UtilisateurRoleId(userId, roleId))
                .utilisateur(utilisateur)
                .role(role)
                .build();
        utilisateurRoleRepository.save(ur);
    }

    @Transactional
    public void removeRoleFromUser(UUID userId, UUID roleId) {
        utilisateurRoleRepository.deleteByUtilisateurIdAndRoleId(userId, roleId);
    }
}