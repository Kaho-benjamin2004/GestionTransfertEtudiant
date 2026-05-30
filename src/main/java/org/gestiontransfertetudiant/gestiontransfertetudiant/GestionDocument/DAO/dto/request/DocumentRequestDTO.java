package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
public class DocumentRequestDTO {
    @NotBlank(message = "Le type de document est obligatoire")
    private String type;

    @NotNull(message = "Le fichier est obligatoire")
    private MultipartFile fichier;
}