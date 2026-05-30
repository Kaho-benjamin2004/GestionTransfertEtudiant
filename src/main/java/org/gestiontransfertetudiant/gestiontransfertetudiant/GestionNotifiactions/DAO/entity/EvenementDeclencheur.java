package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "evenement_declencheur")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EvenementDeclencheur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nom;
    private String description;
    private String templateEmail;
    private String templateSMS;
    private String templateInApp;
}
