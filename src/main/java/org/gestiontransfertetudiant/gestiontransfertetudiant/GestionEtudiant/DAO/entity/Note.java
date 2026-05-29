package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "note")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal valeur;

    private String session; // "Normale", "Rattrapage"

    private LocalDate dateObtention;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcours_academique_id", nullable = false)
    private ParcoursAcademique parcoursAcademique;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unite_enseignement_id", nullable = false)
    private UniteEnseignement uniteEnseignement;
}