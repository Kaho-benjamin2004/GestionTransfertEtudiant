package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "piece_justificative")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PieceJustificative {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nomFichier;
    private String type;
    private String chemin;
    private LocalDateTime dateUpload;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_id", nullable = false)
    private DemandeTransfert demande;
}