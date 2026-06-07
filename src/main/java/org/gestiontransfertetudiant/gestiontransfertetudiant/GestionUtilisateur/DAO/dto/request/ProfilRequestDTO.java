package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProfilRequestDTO {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    // ProfilRequestDTO.java
// Ajoutez ces champs (ils ne seront pas envoyés via formulaire mais via des endpoints dédiés)
    private String photoUrl;
    private String coverPhotoUrl;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    private String telephone;

    private String matriculeNational;
}