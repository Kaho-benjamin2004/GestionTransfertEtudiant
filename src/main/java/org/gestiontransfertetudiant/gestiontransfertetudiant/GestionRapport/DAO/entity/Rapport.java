package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "rapport")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Rapport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nom;
    private String type; // PDF, CSV, EXCEL
    private String chemin;
    private LocalDateTime dateGeneration;
    private String parametres; // JSON des paramètres utilisés
}
