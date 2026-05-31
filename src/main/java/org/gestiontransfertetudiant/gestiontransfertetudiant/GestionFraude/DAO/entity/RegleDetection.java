package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionFraude.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "regle_detection")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class RegleDetection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nom;
    private String description;
    private String type; // CONNEXION, TRANSFERT, DOCUMENT
    @Column(columnDefinition = "TEXT")
    private String parametres; // JSON
    private Boolean actif;
    private Integer seuil;
    @OneToMany(mappedBy = "regle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alerte> alertes = new ArrayList<>();
}
