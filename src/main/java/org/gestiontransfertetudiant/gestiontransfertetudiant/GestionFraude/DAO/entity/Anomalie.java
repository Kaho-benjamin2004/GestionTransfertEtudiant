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
public class Anomalie {
    @Id
    @GeneratedValue
    private UUID id;
    private String typeAnomalie;
    private String niveau; // INFO, AVERTISSEMENT, CRITIQUE
    private String message;
    private LocalDateTime dateDetection;
    private UUID entiteConcerneeId;
    private String entiteType; // DEMANDE_TRANSFERT, DOCUMENT, etc.
    private Boolean resolue;
    private LocalDateTime dateResolution;
    private String commentaireResolution;
}