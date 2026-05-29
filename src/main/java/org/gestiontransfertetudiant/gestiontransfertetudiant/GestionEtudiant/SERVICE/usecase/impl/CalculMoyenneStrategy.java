package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.usecase.impl;

import java.math.BigDecimal;
import java.util.UUID;

public interface CalculMoyenneStrategy {
    BigDecimal calculer(UUID etudiantId);
}
