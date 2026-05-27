package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name = "profil")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profil {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 20)
    private String telephone;

    private String matriculeNational;

    private String fonction;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false, unique = true)
    private Utilisateur utilisateur;
}