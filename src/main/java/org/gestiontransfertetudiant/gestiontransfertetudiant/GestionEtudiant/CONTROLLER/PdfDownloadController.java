package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.CONTROLLER;

import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.usecase.IEtudiantMetier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PdfDownloadController {

    private final IEtudiantMetier etudiantMetier;

    @GetMapping("/etudiant/download/releve")
    public ResponseEntity<byte[]> downloadReleve(@RequestParam UUID etudiantId, @RequestParam UUID parcoursId) {
        byte[] pdf = etudiantMetier.genererReleveNotes(etudiantId, parcoursId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=releve_notes.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}