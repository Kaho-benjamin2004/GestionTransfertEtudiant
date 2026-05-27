package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.mapper.ProfilMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.mapper.UtilisateurMapper;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.ProfilRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.UtilisateurRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.UtilisateurUpdateRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.MessageResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.UtilisateurDetailResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.UtilisateurResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.UtilisateurSimpleResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Profil;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Utilisateur;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.RoleRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.UtilisateurRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.AlreadyExistsException;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurRoleService utilisateurRoleService; // à créer

    @Transactional
    public UtilisateurResponseDTO createUser(UtilisateurRequestDTO request, ProfilRequestDTO profilRequest) {
        if (utilisateurRepository.existsByLogin(request.getLogin()))
            throw new AlreadyExistsException("Login déjà utilisé");
        Utilisateur utilisateur = UtilisateurMapper.toEntity(request);
        utilisateur.setMotDePasseHash(passwordEncoder.encode(request.getMotDePasse()));
        utilisateur = utilisateurRepository.save(utilisateur);
        Profil profil = ProfilMapper.toEntity(profilRequest, utilisateur);
        utilisateur.setProfil(profil);
        utilisateur = utilisateurRepository.save(utilisateur);
        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            request.getRoleIds().forEach(roleId -> utilisateurRoleService.assignRoleToUser(utilisateur.getId(), roleId));
        }
        return UtilisateurMapper.toDTO(utilisateur);
    }

    @Transactional(readOnly = true)
    public UtilisateurDetailResponseDTO getUserById(UUID id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", id));
        return UtilisateurMapper.toDetailDTO(utilisateur);
    }

    @Transactional(readOnly = true)
    public Page<UtilisateurSimpleResponseDTO> getAllUsers(Pageable pageable) {
        return utilisateurRepository.findAll(pageable)
                .map(UtilisateurMapper::toSimpleDTO);
    }

    @Transactional
    public UtilisateurResponseDTO updateUser(UUID id, UtilisateurUpdateRequestDTO request) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", id));
        if (request.getLogin() != null) utilisateur.setLogin(request.getLogin());
        if (request.getActif() != null) utilisateur.setActif(request.getActif());
        utilisateur = utilisateurRepository.save(utilisateur);
        return UtilisateurMapper.toDTO(utilisateur);
    }

    @Transactional
    public MessageResponseDTO deleteUser(UUID id) {
        if (!utilisateurRepository.existsById(id)) throw new ResourceNotFoundException("Utilisateur", id);
        utilisateurRepository.deleteById(id);
        return MessageResponseDTO.builder().message("Utilisateur supprimé").success(true).statusCode(200).build();
    }
}