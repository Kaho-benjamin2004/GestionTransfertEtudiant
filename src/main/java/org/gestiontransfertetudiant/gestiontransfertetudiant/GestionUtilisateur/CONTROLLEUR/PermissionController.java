package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.CONTROLLEUR;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.PermissionRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.PermissionResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/permissions")
public class PermissionController {

    private final PermissionService permissionService;



    @GetMapping
    public String listPermissions(Model model) {
        List<PermissionResponseDTO> permissions = permissionService.getAllPermissions();
        model.addAttribute("permissions", permissions);
        return "admin/permissions/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("permissionRequest")) {
            model.addAttribute("permissionRequest", new PermissionRequestDTO());
        }
        return "admin/permissions/create";
    }

    @PostMapping("/create")
    public String processCreate(@Valid @ModelAttribute("permissionRequest") PermissionRequestDTO request,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/permissions/create";
        }
        try {
            permissionService.createPermission(request);
            redirectAttributes.addFlashAttribute("success", "Permission créée.");
            return "redirect:/admin/permissions";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/permissions/create";
        }
    }

    @GetMapping("/{id}")
    public String viewPermission(@PathVariable UUID id, Model model) {
        PermissionResponseDTO permission = permissionService.getPermission(id);
        model.addAttribute("permission", permission);
        return "admin/permissions/view";
    }
    @PostMapping("/delete/{id}")
    public String deletePermission(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            permissionService.deletePermission(id);
            redirectAttributes.addFlashAttribute("success", "Permission supprimée.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/permissions";
    }
}