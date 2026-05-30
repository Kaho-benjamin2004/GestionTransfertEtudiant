package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity;

import jakarta.persistence.*;
import lombok.*;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity.Etudiant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "demande_transfert")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class DemandeTransfert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDate dateDemande;
    private String motif;
    private String statut; // BROUILLON, SOUMISE, EN_COURS_ORIGINE, EN_COURS_EQUIVALENCE, ACCEPTEE, REFUSEE, ANNULEE
    private String commentaireRefus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;
    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PieceJustificative> pieces = new ArrayList<>();
    @OneToOne(mappedBy = "demande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Equivalence equivalence;
    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransfertAudit> audits = new ArrayList<>();
}
