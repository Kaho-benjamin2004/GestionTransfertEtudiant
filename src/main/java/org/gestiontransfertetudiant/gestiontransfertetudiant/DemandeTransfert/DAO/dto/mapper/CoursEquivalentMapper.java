package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request.CoursEquivalentRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.response.CoursEquivalentResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity.CoursEquivalent;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity.Equivalence;

public class CoursEquivalentMapper {

    public static CoursEquivalentResponseDTO toDTO(CoursEquivalent cours) {
        if (cours == null) return null;
        return CoursEquivalentResponseDTO.builder()
                .id(cours.getId())
                .codeCoursOrigine(cours.getCodeCoursOrigine())
                .intituleCoursOrigine(cours.getIntituleCoursOrigine())
                .codeCoursCible(cours.getCodeCoursCible())
                .intituleCoursCible(cours.getIntituleCoursCible())
                .credits(cours.getCredits())
                .build();
    }

    public static CoursEquivalent toEntity(CoursEquivalentRequestDTO requestDTO, Equivalence equivalence) {
        if (requestDTO == null) return null;
        CoursEquivalent cours = new CoursEquivalent();
        cours.setCodeCoursOrigine(requestDTO.getCodeCoursOrigine());
        cours.setIntituleCoursOrigine(requestDTO.getIntituleCoursOrigine());
        cours.setCodeCoursCible(requestDTO.getCodeCoursCible());
        cours.setIntituleCoursCible(requestDTO.getIntituleCoursCible());
        cours.setCredits(requestDTO.getCredits());
        cours.setEquivalence(equivalence);
        return cours;
    }
}