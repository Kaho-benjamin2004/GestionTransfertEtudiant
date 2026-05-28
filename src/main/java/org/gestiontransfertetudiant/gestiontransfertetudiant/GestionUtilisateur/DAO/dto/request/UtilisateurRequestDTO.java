package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UtilisateurRequestDTO {
    @NotBlank(message = "Le login est obligatoire")
    @Size(min = 3, max = 50)
    private String login;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8)
    private String motDePasse;

    private Boolean actif = true;

    private Set<UUID> roleIds;

    // Champs du profil
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email
    private String email;

    private String telephone;

    private String matriculeNational;
}