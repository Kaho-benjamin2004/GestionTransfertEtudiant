package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UtilisateurUpdateRequestDTO {
    @Size(min = 3, max = 50, message = "Le login doit contenir entre 3 et 50 caractères")
    private String login;

    private Boolean actif;
}