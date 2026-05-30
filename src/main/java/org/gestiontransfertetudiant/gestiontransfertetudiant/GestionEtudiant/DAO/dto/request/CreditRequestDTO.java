package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CreditRequestDTO {
    @NotNull(message = "Le nombre de crédits est obligatoire")
    @Min(value = 0, message = "Le nombre doit être >= 0")
    private Integer nombre;

    private String annee;
    private String statut; // "Acquis", "Non acquis", "En cours"

    @NotNull(message = "L'ID du parcours académique est obligatoire")
    private UUID parcoursAcademiqueId;
}