package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.mapper;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.request.EtablissementAnterieurRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response.EtablissementAnterieurResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.EtablissementAnterieur;

public class EtablissementAnterieurMapper {

    public static EtablissementAnterieurResponseDTO toDTO(EtablissementAnterieur etab) {
        if (etab == null) return null;
        return EtablissementAnterieurResponseDTO.builder()
                .id(etab.getId())
                .nom(etab.getNom())
                .ville(etab.getVille())
                .pays(etab.getPays())
                .anneeDebut(etab.getAnneeDebut())
                .anneeFin(etab.getAnneeFin())
                .build();
    }

    public static EtablissementAnterieur toEntity(EtablissementAnterieurRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        EtablissementAnterieur etab = new EtablissementAnterieur();
        etab.setNom(requestDTO.getNom());
        etab.setVille(requestDTO.getVille());
        etab.setPays(requestDTO.getPays());
        etab.setAnneeDebut(requestDTO.getAnneeDebut());
        etab.setAnneeFin(requestDTO.getAnneeFin());
        return etab;
    }
}
