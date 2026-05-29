package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class EtablissementAnterieurRequestDTO {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String ville;
    private String pays;

    @Min(value = 1900, message = "Année début invalide")
    private Integer anneeDebut;

    @Min(value = 1900, message = "Année fin invalide")
    private Integer anneeFin;

    @NotNull(message = "L'ID étudiant est obligatoire")
    private UUID etudiantId;
}