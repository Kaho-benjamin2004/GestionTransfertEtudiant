package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {
    // Le pool par défaut est suffisant
}
