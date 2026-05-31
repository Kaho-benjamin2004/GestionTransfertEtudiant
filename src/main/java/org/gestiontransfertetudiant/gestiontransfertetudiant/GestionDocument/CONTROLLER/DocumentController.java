package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.CONTROLLER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.request.DocumentRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.DAO.dto.response.DocumentResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionDocument.SERVICE.IDocumentMetier;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/etudiant/documents")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ETUDIANT')")
public class DocumentController {

    private final IDocumentMetier documentMetier;

    private UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getId();
    }

    @GetMapping("/deposer")
    public String formDeposerDocument(Model model) {
        if (!model.containsAttribute("documentRequest")) {
            model.addAttribute("documentRequest", new DocumentRequestDTO());
        }
        return "etudiant/documents/deposer";
    }

    @PostMapping("/deposer")
    public String deposerDocument(@Valid @ModelAttribute("documentRequest") DocumentRequestDTO request,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "etudiant/documents/deposer";
        }
        try {
            documentMetier.deposerDocument(request, getCurrentUserId());
            redirectAttributes.addFlashAttribute("success", "Document déposé avec succès.");
            return "redirect:/etudiant/documents/mes-documents";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/etudiant/documents/deposer";
        }
    }
//
//    @GetMapping("/mes-documents")
//    public String mesDocuments(Model model) {
//        List<DocumentResponseDTO> documents = (List<DocumentResponseDTO>) documentMetier.listerDocumentsParProprietaire(getCurrentUserId());
//        model.addAttribute("documents", documents);
//        return "etudiant/documents/liste";
//    }
@GetMapping("/mes-documents")
public String mesDocuments(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           Model model) {
    Page<DocumentResponseDTO> documents = documentMetier.listerDocumentsParProprietaire(getCurrentUserId(), PageRequest.of(page, size));
    model.addAttribute("documents", documents);
    return "etudiant/documents/liste";
}
    @GetMapping("/{id}")
    public String voirDocument(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        try {
            DocumentResponseDTO document = documentMetier.consulterDocument(id);
            model.addAttribute("document", document);
            return "etudiant/documents/voir";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/etudiant/documents/mes-documents";
        }
    }

    @GetMapping("/{id}/versions")
    public String historiqueVersions(@PathVariable UUID id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("versions", documentMetier.historiqueVersions(id));
            model.addAttribute("documentId", id);
            return "etudiant/documents/versions";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/etudiant/documents/mes-documents";
        }
    }
}