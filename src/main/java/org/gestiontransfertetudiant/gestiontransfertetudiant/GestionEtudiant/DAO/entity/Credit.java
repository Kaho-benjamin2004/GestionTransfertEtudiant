package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "credit")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Integer nombre;
    private String annee;
    private String statut;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcours_academique_id", nullable = false)
    private ParcoursAcademique parcoursAcademique;
}