package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "sanction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sanction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate date;

    private String gravite; // "Avertissement", "Blâme", "Exclusion"

    private Integer duree; // en jours (si exclusion)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;
}