package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CoursEquivalentRequestDTO {
    @NotBlank(message = "Le code du cours d'origine est obligatoire")
    private String codeCoursOrigine;

    private String intituleCoursOrigine;

    @NotBlank(message = "Le code du cours cible est obligatoire")
    private String codeCoursCible;

    private String intituleCoursCible;

    @NotNull(message = "Le nombre de crédits est obligatoire")
    @Min(1)
    private Integer credits;
}