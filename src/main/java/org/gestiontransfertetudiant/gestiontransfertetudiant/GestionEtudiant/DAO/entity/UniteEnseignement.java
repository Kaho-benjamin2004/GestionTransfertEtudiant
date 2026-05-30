package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "unite_enseignement")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UniteEnseignement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String code;
    private String intitule;
    private Integer credits;
    private Integer coefficient;
    @OneToMany(mappedBy = "uniteEnseignement")
    private List<Note> notes = new ArrayList<>();
}
