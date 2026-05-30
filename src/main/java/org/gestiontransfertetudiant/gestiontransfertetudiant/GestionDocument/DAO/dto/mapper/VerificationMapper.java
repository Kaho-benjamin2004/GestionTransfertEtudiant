package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.response.VerificationResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity.Document;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity.Verification;

import java.time.LocalDateTime;
import java.util.UUID;

public class VerificationMapper {

    public static VerificationResponseDTO toDTO(Verification verification) {
        if (verification == null) return null;
        return VerificationResponseDTO.builder()
                .id(verification.getId())
                .dateVerification(verification.getDateVerification())
                .resultat(verification.getResultat())
                .commentaire(verification.getCommentaire())
                .verificateurId(verification.getVerificateurId())
                .build();
    }

    public static Verification toEntity(Document document, boolean resultat, String commentaire, UUID verificateurId) {
        if (document == null) return null;
        Verification verification = new Verification();
        verification.setDateVerification(LocalDateTime.now());
        verification.setResultat(resultat);
        verification.setCommentaire(commentaire);
        verification.setVerificateurId(verificateurId);
        verification.setDocument(document);
        return verification;
    }
}