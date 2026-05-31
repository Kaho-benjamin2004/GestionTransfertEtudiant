package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport;

import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.DAO.entity.Rapport;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.DAO.repository.RapportRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.config.BatchJobConfig;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class rRapportService {

    private final JobLauncher jobLauncher;
    private final BatchJobConfig batchJobConfig;
    private final RapportRepository rapportRepository;

    @Async
    public void genererRapport(String type, LocalDateTime debut, LocalDateTime fin, String format, String outputDir) throws Exception {
        String fileName = "rapport_" + type + "_" + System.currentTimeMillis() + "." + format.toLowerCase();
        String filePath = Path.of(outputDir, fileName).toString();

        // Configurer le job avec les paramètres
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("type", type)
                .addString("dateDebut", debut.toString())
                .addString("dateFin", fin.toString())
                .addString("outputPath", filePath)
                .toJobParameters();

        // Lancer le job
        jobLauncher.run(batchJobConfig.genererRapportStatistiquesJob(), jobParameters);

        // Enregistrer le rapport généré en base
        Rapport rapport = Rapport.builder()
                .nom(fileName)
                .type(format)
                .chemin(filePath)
                .dateGeneration(LocalDateTime.now())
                .parametres(String.format("{\"type\":\"%s\",\"debut\":\"%s\",\"fin\":\"%s\"}", type, debut, fin))
                .build();
        rapportRepository.save(rapport);
    }
}