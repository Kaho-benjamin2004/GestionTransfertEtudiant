package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "preference_notification")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PreferenceNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID utilisateurId;
    private String typeEvenement; // TRANSFERT_SOUMIS, TRANSFERT_VALIDE, etc.
    private String canal;         // EMAIL, SMS, IN_APP
    private Boolean actif;
}