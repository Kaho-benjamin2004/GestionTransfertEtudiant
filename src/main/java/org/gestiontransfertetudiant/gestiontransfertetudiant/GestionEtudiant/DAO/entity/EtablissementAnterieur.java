package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "etablissement_anterieur")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EtablissementAnterieur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nom;
    private String ville;
    private String pays;
    private Integer anneeDebut;
    private Integer anneeFin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;
}