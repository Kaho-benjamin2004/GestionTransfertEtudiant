package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.SessionResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity.Session;

public class SessionMapper {

    public static SessionResponseDTO toDTO(Session session) {
        if (session == null) return null;
        return SessionResponseDTO.builder()
                .id(session.getId())
                .token(masquerToken(session.getToken())) // sécurité : afficher seulement les 4 derniers caractères
                .dateDebut(session.getDateDebut())
                .dateFin(session.getDateFin())
                .actif(session.getActif())
                .adresseIP(session.getAdresseIP())
                .userAgent(session.getUserAgent())
                .build();
    }

    private static String masquerToken(String token) {
        if (token == null || token.length() < 8) return "****";
        return "..." + token.substring(token.length() - 4);
    }

    // Pas de toEntity pour Session car les sessions sont créées automatiquement
}