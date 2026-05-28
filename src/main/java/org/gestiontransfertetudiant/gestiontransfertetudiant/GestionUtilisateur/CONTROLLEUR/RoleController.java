package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.CONTROLLEUR;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.RoleRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.PermissionResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.RoleDetailResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.RoleResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.PermissionService;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/roles")
public class RoleController {

    private final RoleService roleService;
    private final PermissionService permissionService;

    @GetMapping
    public String listRoles(Model model) {
        List<RoleResponseDTO> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "admin/roles/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("roleRequest")) {
            model.addAttribute("roleRequest", new RoleRequestDTO());
        }
        model.addAttribute("allPermissions", permissionService.getAllPermissions());
        return "admin/roles/create";
    }

    @PostMapping("/create")
    public String processCreate(@Valid @ModelAttribute("roleRequest") RoleRequestDTO request,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("allPermissions", permissionService.getAllPermissions());
            return "admin/roles/create";
        }
        try {
            roleService.createRole(request);
            redirectAttributes.addFlashAttribute("success", "Rôle créé.");
            return "redirect:/admin/roles";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/roles/create";
        }
    }

    @GetMapping("/{id}")
    public String viewRole(@PathVariable UUID id, Model model) {
        RoleDetailResponseDTO role = roleService.getRoleById(id);
        model.addAttribute("role", role);
        return "admin/roles/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        RoleDetailResponseDTO role = roleService.getRoleById(id);
        RoleRequestDTO request = new RoleRequestDTO();
        request.setNom(role.getNom());
        request.setDescription(role.getDescription());
        // Convertir List en Set
        Set<UUID> currentPermissionIds = role.getPermissions().stream()
                .map(PermissionResponseDTO::getId)
                .collect(Collectors.toSet());
        request.setPermissionIds(currentPermissionIds);

        model.addAttribute("roleRequest", request);
        model.addAttribute("role", role);
        model.addAttribute("roleId", id);
        model.addAttribute("allPermissions", permissionService.getAllPermissions());
        return "admin/roles/edit";
    }

    @PostMapping("/edit/{id}")
    public String processEdit(@PathVariable UUID id,
                              @Valid @ModelAttribute("roleRequest") RoleRequestDTO request,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("allPermissions", permissionService.getAllPermissions());
            model.addAttribute("roleId", id);
            try {
                model.addAttribute("role", roleService.getRoleById(id));
            } catch (Exception ignored) {}
            return "admin/roles/edit";
        }
        try {
            roleService.updateRole(id, request);
            redirectAttributes.addFlashAttribute("success", "Rôle mis à jour.");
            return "redirect:/admin/roles/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/roles/edit/" + id;
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteRole(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            roleService.deleteRole(id);
            redirectAttributes.addFlashAttribute("success", "Rôle supprimé.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/roles";
    }
}