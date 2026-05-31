package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalFraude {
    @Id
    @GeneratedValue
    private UUID id;
    private LocalDateTime dateExecution;
    private String action; // VERIFICATION_PERIODIQUE, DETECTION_MANUELLE
    private Integer nbAnomaliesDetectees;
    private String details;
    private UUID executeurId; // ID de l'utilisateur (admin)
}