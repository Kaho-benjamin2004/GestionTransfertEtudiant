package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "historique_analyse")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class HistoriqueAnalyse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDateTime dateAnalyse;
    private String resultat;
    private String details;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alerte_id")
    private Alerte alerte;
}