package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "document")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nom;
    private String type;   // RELEVE_NOTES, ATTESTATION, CIN, etc.
    private String cheminStockage;
    private String hash;   // SHA-256
    private String signature; // optionnel (électronique)
    private LocalDateTime dateDepot;
    private String statut; // EN_ATTENTE, VALIDE, REJETE
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VersionDocument> versions = new ArrayList<>();
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Verification> verifications = new ArrayList<>();
}