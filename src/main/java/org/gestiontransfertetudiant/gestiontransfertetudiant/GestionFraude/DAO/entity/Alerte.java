package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "alerte")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Alerte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDateTime dateCreation;
    private String type;
    private String niveau; // INFO, WARNING, CRITICAL
    private String message;
    private UUID entiteId;
    private String entiteType;
    private Boolean traitee;
    private String commentaireTraitement;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regle_id")
    private RegleDetection regle;
    @OneToMany(mappedBy = "alerte", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoriqueAnalyse> historiques = new ArrayList<>();
}