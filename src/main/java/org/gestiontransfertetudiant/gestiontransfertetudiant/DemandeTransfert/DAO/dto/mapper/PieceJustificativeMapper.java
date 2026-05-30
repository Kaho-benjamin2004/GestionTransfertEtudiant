package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.request.PieceJustificativeRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.response.PieceJustificativeResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity.DemandeTransfert;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity.PieceJustificative;

import java.time.LocalDateTime;

public class PieceJustificativeMapper {

    public static PieceJustificativeResponseDTO toDTO(PieceJustificative piece) {
        if (piece == null) return null;
        return PieceJustificativeResponseDTO.builder()
                .id(piece.getId())
                .nomFichier(piece.getNomFichier())
                .type(piece.getType())
                .chemin(piece.getChemin())
                .dateUpload(piece.getDateUpload())
                .build();
    }

    public static PieceJustificative toEntity(PieceJustificativeRequestDTO requestDTO, DemandeTransfert demande, String cheminStockage) {
        if (requestDTO == null) return null;
        PieceJustificative piece = new PieceJustificative();
        piece.setType(requestDTO.getType());
        piece.setDemande(demande);
        piece.setDateUpload(LocalDateTime.now());
        piece.setNomFichier(requestDTO.getFichier().getOriginalFilename());
        piece.setChemin(cheminStockage);
        return piece;
    }
}