package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.request.WorkflowRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.dto.response.WorkflowResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.Workflow;

import java.util.stream.Collectors;

public class WorkflowMapper {

    public static WorkflowResponseDTO toDTO(Workflow workflow) {
        if (workflow == null) return null;
        return WorkflowResponseDTO.builder()
                .id(workflow.getId())
                .nom(workflow.getNom())
                .description(workflow.getDescription())
                .actif(workflow.getActif())
                .etapes(workflow.getEtapes() != null ?
                        workflow.getEtapes().stream()
                        .map(EtapeValidationMapper::toDTO)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public static Workflow toEntity(WorkflowRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        Workflow workflow = new Workflow();
        workflow.setNom(requestDTO.getNom());
        workflow.setDescription(requestDTO.getDescription());
        workflow.setActif(true); // par défaut actif
        return workflow;
    }
}
