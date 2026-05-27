package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException(String message) {
        super(message, "INVALID_TOKEN");
    }
}