package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UtilisateurRequestDTO {
    @NotBlank(message = "Le login est obligatoire")
    @Size(min = 3, max = 50, message = "Le login doit contenir entre 3 et 50 caractères")
    private String login;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String motDePasse;

    private Boolean actif; // optionnel, défaut true

    private Set<UUID> roleIds; // pour assigner des rôles directement
}