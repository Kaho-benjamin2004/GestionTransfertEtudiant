package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.CONTROLLER;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/batch")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job exportTransfertsJob;

    @PostMapping("/export-transferts")
    public String declencherExport() throws Exception {
        jobLauncher.run(exportTransfertsJob, new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());
        return "Export déclenché";
    }
}
