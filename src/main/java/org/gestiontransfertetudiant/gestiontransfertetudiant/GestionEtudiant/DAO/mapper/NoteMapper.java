package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request.NoteRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response.NoteResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.Note;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.ParcoursAcademique;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.UniteEnseignement;

public class NoteMapper {

    public static NoteResponseDTO toDTO(Note note) {
        if (note == null) return null;
        return NoteResponseDTO.builder()
                .id(note.getId())
                .valeur(note.getValeur())
                .session(note.getSession())
                .dateObtention(note.getDateObtention())
                .uniteEnseignement(UniteEnseignementMapper.toDTO(note.getUniteEnseignement()))
                .build();
    }

    public static Note toEntity(NoteRequestDTO requestDTO,
                                ParcoursAcademique parcoursAcademique,
                                UniteEnseignement uniteEnseignement) {
        if (requestDTO == null) return null;
        Note note = new Note();
        note.setValeur(requestDTO.getValeur());
        note.setSession(requestDTO.getSession());
        note.setDateObtention(requestDTO.getDateObtention());
        note.setParcoursAcademique(parcoursAcademique);
        note.setUniteEnseignement(uniteEnseignement);
        return note;
    }
}