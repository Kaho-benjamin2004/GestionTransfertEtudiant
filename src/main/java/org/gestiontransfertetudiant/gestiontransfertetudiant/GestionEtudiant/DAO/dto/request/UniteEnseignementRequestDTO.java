package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DTO.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UniteEnseignementRequestDTO {
    @NotBlank(message = "Le code est obligatoire")
    private String code;

    @NotBlank(message = "L'intitulé est obligatoire")
    private String intitule;

    @NotNull(message = "Les crédits sont obligatoires")
    @Min(value = 1, message = "Les crédits doivent être au moins 1")
    private Integer credits;

    @Min(value = 1, message = "Le coefficient doit être au moins 1")
    private Integer coefficient;
}