package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class EtapeValidationRequestDTO {
    @NotNull(message = "L'ordre de l'étape est obligatoire")
    @Min(value = 1, message = "L'ordre doit être >= 1")
    private Integer ordre;

    @NotBlank(message = "Le rôle requis est obligatoire (ex: ROLE_AGENT)")
    private String roleRequis;

    private String statutMinimumRequis; // optionnel
}