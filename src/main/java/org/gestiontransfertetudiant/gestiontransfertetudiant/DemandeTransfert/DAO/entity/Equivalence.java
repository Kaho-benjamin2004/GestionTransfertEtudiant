package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "equivalence")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Equivalence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String commentaire;
    private String statut; // VALIDEE, REJETEE
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_id", unique = true)
    private DemandeTransfert demande;
    @OneToMany(mappedBy = "equivalence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoursEquivalent> coursEquivalents = new ArrayList<>();
}