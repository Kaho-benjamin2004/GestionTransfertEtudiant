package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class ValidationErrorResponseDTO {
    private String message;
    private Map<String, String> errors; // champ -> erreur
    private Integer statusCode;
}
