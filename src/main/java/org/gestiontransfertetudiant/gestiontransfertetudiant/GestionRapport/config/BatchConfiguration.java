package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.config;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.DAO.dto.TransfertExportDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.SERVICE.ITransfertMetier;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfiguration {

    private final ITransfertMetier transfertMetier;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    @StepScope
    public ListItemReader<TransfertExportDTO> reader() {
        LocalDate debut = LocalDate.now().minusMonths(1);
        LocalDate fin = LocalDate.now();
        List<TransfertExportDTO> transferts = transfertMetier.getTransfertsForExport(debut, fin, null);
        return new ListItemReader<>(transferts);
    }

//    @Bean
//    @StepScope
//    public ListItemReader<TransfertExportDTO> reader() {
//        // Calcul des dates (exemple : dernier mois)
//        LocalDate debut = LocalDate.now().minusMonths(1);
//        LocalDate fin = LocalDate.now();
//        List<TransfertExportDTO> transferts = transfertMetier.getTransfertsForExport(debut, fin, null);
//        return new ListItemReader<>(transferts);
//    }

    @Bean
    public FlatFileItemWriter<TransfertExportDTO> writer() {
        return new FlatFileItemWriterBuilder<TransfertExportDTO>()
                .name("transfertWriter")
                .resource(new FileSystemResource("rapports/transferts_" + LocalDate.now() + ".csv"))
                .delimited().delimiter(",")
                .names("id", "etudiantNom", "etudiantPrenom", "dateDemande", "motif", "statut", "universiteOrigine", "universiteCible")
                .build();
    }

    @Bean
    public Step exportTransfertsStep() {
        return new StepBuilder("exportTransfertsStep", jobRepository)
                .<TransfertExportDTO, TransfertExportDTO>chunk(100, transactionManager)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public Job exportTransfertsJob() {
        return new JobBuilder("exportTransfertsJob", jobRepository)
                .start(exportTransfertsStep())
                .build();
    }
}