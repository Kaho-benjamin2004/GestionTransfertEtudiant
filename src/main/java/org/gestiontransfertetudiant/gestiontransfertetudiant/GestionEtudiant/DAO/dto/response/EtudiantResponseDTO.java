package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DTO.response;

import lombok.Builder;
import lombok.Data;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response.EtablissementAnterieurResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response.ParcoursAcademiqueResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.dto.response.SanctionResponseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class EtudiantResponseDTO {
    private UUID id;
    private String numeroEtudiant;
    private LocalDate dateInscription;
    private String parcoursActuel;
    private String niveau;
    private UUID utilisateurId;           // référence vers l'utilisateur associé
    private String nom;                   // issu du profil (dénormalisé)
    private String prenom;
    private String email;
    private List<ParcoursAcademiqueResponseDTO> parcoursAcademiques;
    private List<SanctionResponseDTO> sanctions;
    private List<EtablissementAnterieurResponseDTO> etablissementsAnterieurs;
}