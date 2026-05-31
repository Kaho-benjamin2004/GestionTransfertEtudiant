package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegleDetectionRequestDTO {
    @NotBlank
    private String nom;
    private String description;
    @NotBlank private String type;
    private String parametres; // JSON
    private Boolean actif;
    private Integer seuil;
}