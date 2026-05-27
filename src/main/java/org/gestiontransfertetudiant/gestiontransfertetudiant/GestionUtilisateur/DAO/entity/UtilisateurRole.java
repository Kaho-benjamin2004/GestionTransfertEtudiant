package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "utilisateur_role",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"utilisateur_id", "role_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateurRole {

    @EmbeddedId
    private UtilisateurRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("utilisateurId")
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}

