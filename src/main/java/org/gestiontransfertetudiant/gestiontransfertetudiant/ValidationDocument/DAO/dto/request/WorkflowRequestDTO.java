package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WorkflowRequestDTO {
    @NotBlank(message = "Le nom du workflow est obligatoire")
    private String nom;

    private String description;

    @NotNull(message = "La liste des étapes est obligatoire")
    private List<EtapeValidationRequestDTO> etapes;
}