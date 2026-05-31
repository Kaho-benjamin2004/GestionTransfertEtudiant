package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionTracabilite.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_log", indexes = {
        @Index(name = "idx_audit_date", columnList = "dateAction"),
        @Index(name = "idx_audit_login", columnList = "utilisateurLogin")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String action;
    private String entiteType;
    private String entiteId;
    @Column(length = 2000)
    private String details;
    private String utilisateurLogin;
    private String adresseIP;
    private LocalDateTime dateAction;
}