package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.CONTROLLER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.DAO.dto.request.NotificationRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionNotifiactions.SERVICE.INotificationMetier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/admin/notifications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminNotificationController {

    private final INotificationMetier notificationMetier;

    @GetMapping("/envoyer")
    public String showSendForm(Model model) {
        if (!model.containsAttribute("notificationRequest")) {
            model.addAttribute("notificationRequest", new NotificationRequestDTO());
        }
        return "admin/notifications/envoyer";
    }

    @PostMapping("/envoyer")
    public String sendNotification(@Valid @ModelAttribute("notificationRequest") NotificationRequestDTO request,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/notifications/envoyer";
        }
        try {
            notificationMetier.envoyerNotification(request);
            redirectAttributes.addFlashAttribute("success", "Notification envoyée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/notifications/envoyer";
    }
}
