package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "verification")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDateTime dateVerification;
    private Boolean resultat;
    private String commentaire;
    private UUID verificateurId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;
}
