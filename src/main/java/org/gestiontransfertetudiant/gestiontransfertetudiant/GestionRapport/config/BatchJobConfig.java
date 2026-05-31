package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.config;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.StatistiqueTransfertDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.RapportCsvWriter;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.StatistiqueTransfertReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BatchJobConfig {

    private final StatistiqueTransfertReader statistiqueReader;
    private final RapportCsvWriter rapportCsvWriter;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job genererRapportStatistiquesJob() {
        return new JobBuilder("genererRapportStatistiquesJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(statistiquesStep())
                .build();
    }

    @Bean
    public Step statistiquesStep() {
        return new StepBuilder("statistiquesStep", jobRepository)
                .<StatistiqueTransfertDTO, StatistiqueTransfertDTO>chunk(10, transactionManager)
                .reader(statistiqueReader)
                .writer(rapportCsvWriter)
                .build();
    }

    // Méthode utilitaire pour lancer le job avec des paramètres dynamiques
    public void lancerRapport(LocalDate debut, LocalDate fin, String outputPath) {
        statistiqueReader.init(debut, fin);
        rapportCsvWriter.setFilePath(outputPath);
        // Le lancement se fait via JobLauncher (voir plus bas)
    }
}