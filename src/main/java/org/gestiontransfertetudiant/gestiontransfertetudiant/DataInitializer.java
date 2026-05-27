package org.gestiontransfertetudiant.gestiontransfertetudiant;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Role;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Utilisateur;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.UtilisateurRole;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.UtilisateurRoleId;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.RoleRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.UtilisateurRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.UtilisateurRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final UtilisateurRoleRepository utilisateurRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        // Création du rôle ADMIN
        Role adminRole = roleRepository.findByNom("ADMIN").orElseGet(() -> {
            Role role = new Role();
            role.setNom("ADMIN");
            role.setDescription("Administrateur système");
            return roleRepository.save(role);
        });

        // Création de l'utilisateur admin
        if (utilisateurRepository.findByLogin("admin").isEmpty()) {
            Utilisateur admin = new Utilisateur();
            admin.setLogin("admin");
            admin.setMotDePasseHash(passwordEncoder.encode("admin"));
            admin.setActif(true);
            Utilisateur savedAdmin = utilisateurRepository.save(admin);

            // Assignation du rôle
            UtilisateurRoleId id = new UtilisateurRoleId(savedAdmin.getId(), adminRole.getId());
            UtilisateurRole ur = new UtilisateurRole();
            ur.setId(id);
            ur.setUtilisateur(savedAdmin);
            ur.setRole(adminRole);
            utilisateurRoleRepository.save(ur);
        }
    }
}