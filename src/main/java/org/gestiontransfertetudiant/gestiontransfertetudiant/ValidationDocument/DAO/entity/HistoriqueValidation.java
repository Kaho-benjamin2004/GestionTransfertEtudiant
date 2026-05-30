package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "historique_validation")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class HistoriqueValidation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String action;
    private LocalDateTime dateAction;
    private UUID utilisateurId;
    private String details;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "validation_id", nullable = false)
    private Validation validation;
}