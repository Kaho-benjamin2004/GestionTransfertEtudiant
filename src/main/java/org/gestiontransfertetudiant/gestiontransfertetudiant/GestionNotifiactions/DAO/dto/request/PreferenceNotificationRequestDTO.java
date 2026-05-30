package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PreferenceNotificationRequestDTO {
    @NotBlank(message = "Le type d'événement est obligatoire")
    private String typeEvenement;

    @NotBlank(message = "Le canal est obligatoire (EMAIL, SMS, IN_APP)")
    private String canal;

    private Boolean actif; // par défaut true si non fourni
}