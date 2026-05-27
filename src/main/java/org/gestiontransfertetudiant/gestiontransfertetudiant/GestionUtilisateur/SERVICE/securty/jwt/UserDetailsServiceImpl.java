package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.UtilisateurRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.ResourceNotFoundException;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UtilisateurRepository utilisateurRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) {
        var utilisateur = utilisateurRepository.findByLoginWithRoles(login)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouve",login));
        return UserDetailsImpl.build(utilisateur);
    }
}