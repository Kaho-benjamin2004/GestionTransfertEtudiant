package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenResponseDTO {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
}