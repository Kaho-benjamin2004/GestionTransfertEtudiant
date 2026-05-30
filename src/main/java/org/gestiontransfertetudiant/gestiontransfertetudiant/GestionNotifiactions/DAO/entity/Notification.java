package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID destinataireId;
    private String type; // EMAIL, SMS, IN_APP
    private String titre;
    private String message;
    private LocalDateTime dateEnvoi;
    private Boolean lu;
    private LocalDateTime luDate;
    private Boolean archived;
    private String lien; // URL optionnelle
}