package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request.DemandeTransfertRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.response.DemandeTransfertResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity.DemandeTransfert;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.Etudiant;

import java.util.stream.Collectors;

public class DemandeTransfertMapper {

    public static DemandeTransfertResponseDTO toDTO(DemandeTransfert demande) {
        if (demande == null) return null;
        return DemandeTransfertResponseDTO.builder()
                .id(demande.getId())
                .dateDemande(demande.getDateDemande())
                .motif(demande.getMotif())
                .statut(demande.getStatut())
                .commentaireRefus(demande.getCommentaireRefus())
                .etudiantId(demande.getEtudiant() != null ? demande.getEtudiant().getId() : null)
                .etudiantNom(demande.getEtudiant() != null && demande.getEtudiant().getUtilisateur() != null &&
                        demande.getEtudiant().getUtilisateur().getProfil() != null ?
                        demande.getEtudiant().getUtilisateur().getProfil().getNom() : null)
                .etudiantPrenom(demande.getEtudiant() != null && demande.getEtudiant().getUtilisateur() != null &&
                        demande.getEtudiant().getUtilisateur().getProfil() != null ?
                        demande.getEtudiant().getUtilisateur().getProfil().getPrenom() : null)
                .pieces(demande.getPieces() != null ?
                        demande.getPieces().stream()
                        .map(PieceJustificativeMapper::toDTO)
                        .collect(Collectors.toList()) : null)
                .equivalence(EquivalenceMapper.toDTO(demande.getEquivalence()))
                .audits(demande.getAudits() != null ?
                        demande.getAudits().stream()
                        .map(TransfertAuditMapper::toDTO)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public static DemandeTransfert toEntity(DemandeTransfertRequestDTO requestDTO, Etudiant etudiant) {
        if (requestDTO == null) return null;
        DemandeTransfert demande = new DemandeTransfert();
        demande.setMotif(requestDTO.getMotif());
        demande.setEtudiant(etudiant);
        demande.setStatut("BROUILLON"); // statut initial
        demande.setDateDemande(java.time.LocalDate.now());
        return demande;
    }
}