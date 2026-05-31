package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAsync
@EnableScheduling
public class AsyncSchedulingConfig {
    // Permet l'exécution asynchrone des jobs et la planification
}