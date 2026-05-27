package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption;

public class AuthenticationException extends BusinessException {
    public AuthenticationException(String message) {
        super(message, "AUTH_FAILED");
    }
}


