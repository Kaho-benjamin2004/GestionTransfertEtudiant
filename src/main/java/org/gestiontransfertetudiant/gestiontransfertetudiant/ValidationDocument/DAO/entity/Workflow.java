package org.gestiontransfertetudiant.gestiontransfertetudiant.ValidationDocument.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "workflow")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Workflow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nom;
    private String description;
    private Boolean actif;
    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EtapeValidation> etapes = new ArrayList<>();
}
