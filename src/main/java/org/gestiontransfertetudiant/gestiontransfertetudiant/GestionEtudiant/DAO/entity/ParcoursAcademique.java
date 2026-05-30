package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "parcours_academique")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ParcoursAcademique {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String anneeUniversitaire;
    private BigDecimal moyenne;
    private String statut; // "En cours", "Validé", "Échoué"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;
    @OneToMany(mappedBy = "parcoursAcademique", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();
    @OneToMany(mappedBy = "parcoursAcademique", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Credit> credits = new ArrayList<>();
}