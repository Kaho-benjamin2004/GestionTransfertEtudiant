package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.request.EtapeValidationRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.response.EtapeValidationResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.EtapeValidation;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.Workflow;

public class EtapeValidationMapper {

    public static EtapeValidationResponseDTO toDTO(EtapeValidation etape) {
        if (etape == null) return null;
        return EtapeValidationResponseDTO.builder()
                .id(etape.getId())
                .ordre(etape.getOrdre())
                .roleRequis(etape.getRoleRequis())
                .statutMinimumRequis(etape.getStatutMinimumRequis())
                .build();
    }

    public static EtapeValidation toEntity(EtapeValidationRequestDTO requestDTO, Workflow workflow) {
        if (requestDTO == null) return null;
        EtapeValidation etape = new EtapeValidation();
        etape.setOrdre(requestDTO.getOrdre());
        etape.setRoleRequis(requestDTO.getRoleRequis());
        etape.setStatutMinimumRequis(requestDTO.getStatutMinimumRequis());
        etape.setWorkflow(workflow);
        return etape;
    }
}