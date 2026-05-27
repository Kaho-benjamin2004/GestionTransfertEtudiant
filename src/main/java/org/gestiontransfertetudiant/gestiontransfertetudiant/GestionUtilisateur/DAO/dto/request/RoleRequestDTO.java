package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class RoleRequestDTO {
    @NotBlank(message = "Le nom du rôle est obligatoire")
    @Size(max = 50)
    private String nom;

    private String description;

    private Set<UUID> permissionIds; // pour assigner des permissions directement
}