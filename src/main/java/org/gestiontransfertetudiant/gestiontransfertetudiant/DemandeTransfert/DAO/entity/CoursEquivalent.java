package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "cours_equivalent")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CoursEquivalent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String codeCoursOrigine;
    private String intituleCoursOrigine;
    private String codeCoursCible;
    private String intituleCoursCible;
    private Integer credits;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equivalence_id", nullable = false)
    private Equivalence equivalence;
}