package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto;

import jakarta.validation.Valid;
import lombok.Data;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.ProfilRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.UtilisateurRequestDTO;


@Data
public class RegistrationDTO {
    @Valid
    private UtilisateurRequestDTO utilisateurRequest = new UtilisateurRequestDTO();
    @Valid
    private ProfilRequestDTO profilRequest = new ProfilRequestDTO();
}