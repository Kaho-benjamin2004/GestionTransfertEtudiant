package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.DAO.repository.NoteRepository;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.CalculMoyenneStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CalculMoyenneStandardStrategy implements CalculMoyenneStrategy {

    private final NoteRepository noteRepository;

    @Override
    public BigDecimal calculer(UUID etudiantId) {
        var notes = noteRepository.findByEtudiantId(etudiantId);
        if (notes.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalPoints = BigDecimal.ZERO;
        BigDecimal totalCoeffs = BigDecimal.ZERO;
        for (var note : notes) {
            BigDecimal coeff = BigDecimal.valueOf(note.getUniteEnseignement().getCoefficient());
            totalPoints = totalPoints.add(note.getValeur().multiply(coeff));
            totalCoeffs = totalCoeffs.add(coeff);
        }
        if (totalCoeffs.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return totalPoints.divide(totalCoeffs, 2, RoundingMode.HALF_UP);
    }
}