package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class RefreshTokenRequestDTO {
    @NotBlank(message = "Le token de rafraîchissement est obligatoire")
    private String refreshToken;
}