package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String resourceName, Object id) {
        super(resourceName + " non trouvé avec l'identifiant : " + id, "NOT_FOUND");
    }
}
