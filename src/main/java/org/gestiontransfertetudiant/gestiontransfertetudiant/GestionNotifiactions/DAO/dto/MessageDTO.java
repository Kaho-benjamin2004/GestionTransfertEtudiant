package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDTO {
    private String message;
    private boolean success;
}