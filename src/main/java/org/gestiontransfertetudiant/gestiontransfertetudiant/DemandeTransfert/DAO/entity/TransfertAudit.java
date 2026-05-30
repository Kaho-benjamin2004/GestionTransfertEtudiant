package org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity;

import jakarta.persistence.*;
import lombok.*;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.entity.DemandeTransfert;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transfert_audit")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class TransfertAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String action;
    private LocalDateTime dateAction;
    private UUID utilisateurId;
    private String details;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demande_id", nullable = false)
    private DemandeTransfert demande;
}
