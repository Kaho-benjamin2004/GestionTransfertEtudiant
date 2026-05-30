package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class PieceJustificativeRequestDTO {
    @NotBlank(message = "Le type de pièce est obligatoire")
    private String type; // ex: "RELEVE_NOTES", "CIN", "ATTESTATION"

    @NotNull(message = "Le fichier est obligatoire")
    private MultipartFile fichier; // pour l'upload
}