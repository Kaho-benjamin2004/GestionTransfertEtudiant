package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.StatistiqueTransfertDTO;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Setter
@Slf4j
@Component
public class RapportCsvWriter implements ItemWriter<StatistiqueTransfertDTO> {

    private String filePath;

    @Override
    public void write(Chunk<? extends StatistiqueTransfertDTO> chunk) throws Exception {
        if (filePath == null) {
            throw new IllegalStateException("Le chemin du fichier n'a pas été défini");
        }
        Path path = Paths.get(filePath);
        boolean fileExists = Files.exists(path);
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            if (!fileExists) {
                writer.println("Date,Nb Demandes,Taux Validation (%)");
            }
            for (StatistiqueTransfertDTO stat : chunk.getItems()) {
                writer.printf("%s,%d,%.2f%n", stat.getDate(), stat.getNbDemandes(), stat.getTauxValidation());
            }
        }
        log.info("Écriture de {} lignes dans {}", chunk.size(), filePath);
    }
}