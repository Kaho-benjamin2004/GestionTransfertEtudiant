package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.SERVICE;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.BusinessException;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.execption.ResourceNotFoundException;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.request.ValidationRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.request.WorkflowRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.response.ValidationResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.response.WorkflowResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IValidationMetier {

    // Soumettre une entité à validation
    ValidationResponseDTO soumettreValidation(ValidationRequestDTO request, UUID soumissionneurId) throws BusinessException;

    // Valider une entité (par le rôle approprié)
    ValidationResponseDTO valider(UUID validationId, UUID valideurId, String commentaire) throws BusinessException;

    // Rejeter une entité (avec motif)
    ValidationResponseDTO rejeter(UUID validationId, UUID valideurId, String motif) throws BusinessException;

    // Demander une révision
    ValidationResponseDTO demanderRevision(UUID validationId, UUID demandeurId, String commentaire) throws BusinessException;

    // Consulter l'historique des validations d'une entité
    List<ValidationResponseDTO> getHistoriqueValidations(UUID entiteId, String entiteType);

    // Annuler une validation (admin)
    void annulerValidation(UUID validationId, UUID annuleurId) throws BusinessException;

    // Attribuer un validateur à une étape (admin)
    void attribuerValidateur(UUID validationId, UUID valideurId, UUID adminId) throws BusinessException;

    // Définir un workflow (admin)
    WorkflowResponseDTO definirWorkflow(WorkflowRequestDTO request, UUID adminId) throws BusinessException;

    // Suspendre une validation (admin)
    void suspendreValidation(UUID validationId, UUID adminId, String raison) throws BusinessException;

    // Reprendre une validation suspendue (admin)
    void reprendreValidation(UUID validationId, UUID adminId) throws BusinessException;
}
