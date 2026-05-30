package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class EtudiantRequestDTO {
    @NotBlank(message = "Le numéro étudiant est obligatoire")
    private String numeroEtudiant;

    @NotNull(message = "La date d'inscription est obligatoire")
    @PastOrPresent(message = "La date d'inscription ne peut être future")
    private LocalDate dateInscription;

    private String parcoursActuel;
    private String niveau;

    @NotNull(message = "L'ID utilisateur est obligatoire")
    private UUID utilisateurId;
}