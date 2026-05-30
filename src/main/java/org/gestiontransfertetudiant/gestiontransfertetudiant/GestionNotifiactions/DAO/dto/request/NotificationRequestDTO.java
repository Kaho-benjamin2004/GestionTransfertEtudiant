package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class NotificationRequestDTO {
    @NotNull(message = "L'ID du destinataire est obligatoire")
    private UUID destinataireId;

    @NotBlank(message = "Le type de notification est obligatoire (EMAIL, SMS, IN_APP)")
    private String type;

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    @NotBlank(message = "Le message est obligatoire")
    private String message;

    private String lien; // optionnel
}