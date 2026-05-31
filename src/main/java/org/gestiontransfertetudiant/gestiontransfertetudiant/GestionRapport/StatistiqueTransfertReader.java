package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.StatistiqueTransfertDTO;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatistiqueTransfertReader implements ItemReader<StatistiqueTransfertDTO> {

    private Iterator<StatistiqueTransfertDTO> iterator;

    // Cette méthode serait appelée avant l'exécution du step pour initialiser les données
    public void init(LocalDate debut, LocalDate fin) {
        // Simulation : en réalité, on irait chercher les données via un repository
        List<StatistiqueTransfertDTO> stats = Arrays.asList(
                StatistiqueTransfertDTO.builder().date(LocalDate.now()).nbDemandes(5).tauxValidation(80.0).build(),
                StatistiqueTransfertDTO.builder().date(LocalDate.now().minusDays(1)).nbDemandes(3).tauxValidation(66.0).build()
        );
        this.iterator = stats.iterator();
    }

    @Override
    public StatistiqueTransfertDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (iterator != null && iterator.hasNext()) {
            return iterator.next();
        }
        return null; // fin de la lecture
    }
}