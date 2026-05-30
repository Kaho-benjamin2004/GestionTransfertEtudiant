package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity;

import jakarta.persistence.*;
import lombok.*;
import org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity.Workflow;

import java.util.UUID;

@Entity
@Table(name = "etape_validation")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EtapeValidation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Integer ordre;
    private String roleRequis; // ROLE_AGENT, ROLE_COMMISSION, etc.
    private String statutMinimumRequis;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;
}
