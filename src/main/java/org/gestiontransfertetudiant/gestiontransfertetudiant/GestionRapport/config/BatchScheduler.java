package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job exportTransfertsJob;

    @Scheduled(cron = "0 0 3 * * *") // tous les jours à 3h du matin
    public void runExportJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(exportTransfertsJob, params);
            log.info("Job d'export des transferts exécuté avec succès");
        } catch (Exception e) {
            log.error("Erreur lors de l'exécution du job d'export", e);
        }
    }
}