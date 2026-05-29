package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request.EtudiantRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.Etudiant;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Utilisateur;

import java.util.stream.Collectors;

public class EtudiantMapper {

    public static org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DTO.response.EtudiantResponseDTO toDTO(Etudiant etudiant) {
        if (etudiant == null) return null;
        return org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DTO.response.EtudiantResponseDTO.builder()
                .id(etudiant.getId())
                .numeroEtudiant(etudiant.getNumeroEtudiant())
                .dateInscription(etudiant.getDateInscription())
                .parcoursActuel(etudiant.getParcoursActuel())
                .niveau(etudiant.getNiveau())
                .utilisateurId(etudiant.getUtilisateur() != null ? etudiant.getUtilisateur().getId() : null)
                .nom(etudiant.getUtilisateur() != null && etudiant.getUtilisateur().getProfil() != null ?
                        etudiant.getUtilisateur().getProfil().getNom() : null)
                .prenom(etudiant.getUtilisateur() != null && etudiant.getUtilisateur().getProfil() != null ?
                        etudiant.getUtilisateur().getProfil().getPrenom() : null)
                .email(etudiant.getUtilisateur() != null && etudiant.getUtilisateur().getProfil() != null ?
                        etudiant.getUtilisateur().getProfil().getEmail() : null)
                .parcoursAcademiques(etudiant.getParcoursAcademiques() != null ?
                        etudiant.getParcoursAcademiques().stream()
                        .map(ParcoursAcademiqueMapper::toDTO)
                        .collect(Collectors.toList()) : null)
                .sanctions(etudiant.getSanctions() != null ?
                        etudiant.getSanctions().stream()
                        .map(SanctionMapper::toDTO)
                        .collect(Collectors.toList()) : null)
                .etablissementsAnterieurs(etudiant.getEtablissementsAnterieurs() != null ?
                        etudiant.getEtablissementsAnterieurs().stream()
                        .map(EtablissementAnterieurMapper::toDTO)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public static Etudiant toEntity(EtudiantRequestDTO requestDTO, Utilisateur utilisateur) {
        if (requestDTO == null) return null;
        Etudiant etudiant = new Etudiant();
        etudiant.setNumeroEtudiant(requestDTO.getNumeroEtudiant());
        etudiant.setDateInscription(requestDTO.getDateInscription());
        etudiant.setParcoursActuel(requestDTO.getParcoursActuel());
        etudiant.setNiveau(requestDTO.getNiveau());
        etudiant.setUtilisateur(utilisateur);
        return etudiant;
    }
}