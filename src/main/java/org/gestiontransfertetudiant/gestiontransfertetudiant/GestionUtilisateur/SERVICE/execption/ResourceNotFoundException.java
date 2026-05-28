package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption;

public class ResourceNotFoundException extends BusinessException {
    // Constructeur avec un seul message (pour les cas où on ne fournit pas d'ID)
    public ResourceNotFoundException(String message) {
        super(message, "NOT_FOUND");
    }

    // Constructeur avec ressource et ID (pour un message plus détaillé)
    public ResourceNotFoundException(String resourceName, Object id) {
        super(resourceName + " non trouvé avec l'identifiant : " + id, "NOT_FOUND");
    }
}