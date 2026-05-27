package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class PermissionRequestDTO {
    @NotBlank(message = "Le nom de la permission est obligatoire")
    @Size(max = 100)
    private String nom;

    @NotBlank(message = "La ressource est obligatoire")
    @Size(max = 100)
    private String ressource;

    @NotBlank(message = "L'action est obligatoire")
    @Size(max = 50)
    private String action;
}