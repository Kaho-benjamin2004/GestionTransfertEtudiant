package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "version_document")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class VersionDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Integer version;
    private String cheminStockage;
    private String hash;
    private LocalDateTime dateModification;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;
}