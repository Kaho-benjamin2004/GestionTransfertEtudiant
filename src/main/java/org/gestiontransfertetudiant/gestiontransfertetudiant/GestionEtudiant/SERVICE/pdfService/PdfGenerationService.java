package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.pdfService;

import com.lowagie.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PdfGenerationService {

    private final TemplateEngine templateEngine;

    /**
     * Génère un PDF à partir d'un template Thymeleaf.
     *
     * @param templateName Nom du template HTML (ex: "pdf/releve_notes")
     * @param context Objet contenant les variables à injecter dans le template
     * @return Tableau de bytes représentant le fichier PDF
     * @throws RuntimeException Si la génération échoue
     */
    public byte[] generatePdf(String templateName, Context context) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // 1. Rendre le template HTML
            String htmlContent = templateEngine.process(templateName, context);
            log.debug("HTML généré avec succès pour le template: {}", templateName);

            // 2. Convertir le HTML en PDF avec Flying Saucer
            ITextRenderer renderer = new ITextRenderer();

            // 3. Configurer le support des polices (crucial pour les caractères accentués)
            // Assurez-vous que le fichier "fonts/DejaVuSans.ttf" est dans le dossier resources.
            // DejaVuSans est une police open-source qui offre un large support Unicode.
            renderer.getFontResolver().addFont("classpath:fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);

            log.info("PDF généré avec succès à partir du template: {}", templateName);
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("Erreur lors de la génération du PDF à partir du template '{}' : {}", templateName, e.getMessage(), e);
            throw new RuntimeException("La génération du PDF a échoué.", e);
        }
    }
}