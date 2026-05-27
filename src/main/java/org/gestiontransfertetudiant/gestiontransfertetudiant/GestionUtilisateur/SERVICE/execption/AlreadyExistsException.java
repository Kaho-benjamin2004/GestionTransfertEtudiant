package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption;

public class AlreadyExistsException extends BusinessException {
    public AlreadyExistsException(String message) {
        super(message, "ALREADY_EXISTS");
    }
}
