package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "journal_connexion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalConnexion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @CreationTimestamp
    private LocalDateTime dateHeure;

    @Column(nullable = false)
    private Boolean succes;

    @Column(length = 45)
    private String adresseIP;

    private String raisonEchec;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;
}