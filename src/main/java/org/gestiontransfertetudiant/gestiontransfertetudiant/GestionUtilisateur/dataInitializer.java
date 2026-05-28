package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.init;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Role;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Utilisateur;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.RoleRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class dataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        // Création du rôle ADMIN
        if (roleRepository.findByNom("ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setNom("ADMIN");
            adminRole.setDescription("Administrateur système");
            roleRepository.save(adminRole);
        }

        // Création du rôle ETUDIANT (indispensable pour l'inscription)
        if (roleRepository.findByNom("ETUDIANT").isEmpty()) {
            Role etudiantRole = new Role();
            etudiantRole.setNom("ETUDIANT");
            etudiantRole.setDescription("Étudiant standard");
            roleRepository.save(etudiantRole);
        }

        // Création d'un compte admin par défaut (login: admin, mot de passe: admin)
        if (utilisateurRepository.findByLogin("admin").isEmpty()) {
            Utilisateur admin = new Utilisateur();
            admin.setLogin("admin");
            admin.setMotDePasseHash(passwordEncoder.encode("admin"));
            admin.setActif(true);
            utilisateurRepository.save(admin);
            // Attribuer le rôle ADMIN à cet utilisateur (à faire via une table de liaison)
            // Vous devez avoir une méthode pour assigner un rôle.
            // Si vous avez un service UtilisateurRoleService, utilisez-le.
            // Sinon, faites directement une requête SQL ou ajoutez une ligne dans utilisateur_role.
        }
    }
}