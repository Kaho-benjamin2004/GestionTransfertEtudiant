package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.repository;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProfilRepository extends JpaRepository<Profil, UUID> {
    Optional<Profil> findByEmail(String email);
    Optional<Profil> findByUtilisateurId(UUID utilisateurId);
    
    Optional<Profil> findByMatriculeNational(String matriculeNational);
}