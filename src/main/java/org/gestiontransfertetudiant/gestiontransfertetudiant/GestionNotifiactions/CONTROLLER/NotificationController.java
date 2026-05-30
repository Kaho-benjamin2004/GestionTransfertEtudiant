package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.CONTROLLER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.request.PreferenceNotificationRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.response.NotificationResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.response.PreferenceNotificationResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.SERVICE.INotificationMetier;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/notifications")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class NotificationController {

    private final INotificationMetier notificationMetier;

    private UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getId();
    }

    // Liste des notifications (paginiée)
    @GetMapping
    public String listNotifications(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size,
                                    Model model) {
        Page<NotificationResponseDTO> notifications = notificationMetier.getNotificationsByUser(getCurrentUserId(), PageRequest.of(page, size));
        long nonLues = notificationMetier.getNombreNonLues(getCurrentUserId());
        model.addAttribute("notifications", notifications);
        model.addAttribute("nonLues", nonLues);
        return "notifications/list";
    }

    // Marquer une notification comme lue
    @PostMapping("/{id}/lire")
    public String marquerCommeLue(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            notificationMetier.marquerCommeLue(id, getCurrentUserId());
            redirectAttributes.addFlashAttribute("success", "Notification marquée comme lue.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/notifications";
    }

    // Archiver une notification
    @PostMapping("/{id}/archiver")
    public String archiver(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            notificationMetier.archiverNotification(id, getCurrentUserId());
            redirectAttributes.addFlashAttribute("success", "Notification archivée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/notifications";
    }

    // Supprimer définitivement une notification
    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            notificationMetier.supprimerNotification(id, getCurrentUserId());
            redirectAttributes.addFlashAttribute("success", "Notification supprimée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/notifications";
    }

    // Afficher le formulaire de configuration des préférences
    @GetMapping("/preferences")
    public String showPreferencesForm(Model model) {
        List<PreferenceNotificationResponseDTO> preferences = notificationMetier.getPreferencesByUser(getCurrentUserId());
        if (!model.containsAttribute("preferenceRequest")) {
            model.addAttribute("preferenceRequest", new PreferenceNotificationRequestDTO());
        }
        model.addAttribute("preferences", preferences);
        return "notifications/preferences";
    }

    // Enregistrer une préférence
    @PostMapping("/preferences")
    public String savePreference(@Valid @ModelAttribute("preferenceRequest") PreferenceNotificationRequestDTO request,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("preferences", notificationMetier.getPreferencesByUser(getCurrentUserId()));
            return "notifications/preferences";
        }
        try {
            notificationMetier.configurerPreference(getCurrentUserId(), request);
            redirectAttributes.addFlashAttribute("success", "Préférence enregistrée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/notifications/preferences";
    }

    // Afficher le compteur de notifications non lues (utilisable en AJAX)
    @GetMapping("/non-lues/count")
    @ResponseBody
    public long countNonLues() {
        return notificationMetier.getNombreNonLues(getCurrentUserId());
    }
}