package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.CONTROLLEUR;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.AssignRoleRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.ProfilRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.UtilisateurRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.request.UtilisateurUpdateRequestDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.RoleResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.UtilisateurDetailResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.UtilisateurSimpleResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.RoleService;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.UtilisateurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final RoleService roleService;

    // Liste paginée des utilisateurs
    @GetMapping
    public String listUtilisateurs(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   Model model) {
        Page<UtilisateurSimpleResponseDTO> utilisateurs = utilisateurService.getAllUsers(PageRequest.of(page, size));
        model.addAttribute("utilisateurs", utilisateurs);
        return "admin/utilisateurs/list";
    }

    // Détail d'un utilisateur avec ses rôles
    @GetMapping("/{id}")
    public String viewUtilisateur(@PathVariable UUID id, Model model) {
        UtilisateurDetailResponseDTO utilisateur = utilisateurService.getUserById(id);
        List<RoleResponseDTO> allRoles = roleService.getAllRoles();
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("assignRoleRequest", new AssignRoleRequestDTO());
        return "admin/utilisateurs/view";
    }

    // Formulaire création utilisateur (admin)
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("utilisateurRequest")) {
            model.addAttribute("utilisateurRequest", new UtilisateurRequestDTO());
            model.addAttribute("profilRequest", new ProfilRequestDTO());
        }
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin/utilisateurs/create";
    }

    // Traiter création
    @PostMapping("/create")
    public String processCreate(@Valid @ModelAttribute("utilisateurRequest") UtilisateurRequestDTO utilisateurRequest,
                                BindingResult resultUser,
                                @Valid @ModelAttribute("profilRequest") ProfilRequestDTO profilRequest,
                                BindingResult resultProfil,
                                RedirectAttributes redirectAttributes) {
        if (resultUser.hasErrors() || resultProfil.hasErrors()) {
            return "admin/utilisateurs/create";
        }
        try {
            utilisateurService.createUser(utilisateurRequest, profilRequest);
            redirectAttributes.addFlashAttribute("success", "Utilisateur créé avec succès.");
            return "redirect:/admin/utilisateurs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/utilisateurs/create";
        }
    }

    // Formulaire édition utilisateur
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        UtilisateurDetailResponseDTO utilisateur = utilisateurService.getUserById(id);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("updateRequest", new UtilisateurUpdateRequestDTO());
        return "admin/utilisateurs/edit";
    }

    // Traiter édition
    @PostMapping("/edit/{id}")
    public String processEdit(@PathVariable UUID id,
                              @Valid @ModelAttribute("updateRequest") UtilisateurUpdateRequestDTO request,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/utilisateurs/edit";
        }
        try {
            utilisateurService.updateUser(id, request);
            redirectAttributes.addFlashAttribute("success", "Utilisateur mis à jour.");
            return "redirect:/admin/utilisateurs/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/utilisateurs/edit/" + id;
        }
    }

    // Supprimer utilisateur
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            utilisateurService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "Utilisateur supprimé.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/utilisateurs";
    }

//    // Assigner un rôle à un utilisateur (AJAX ou formulaire)
//    @PostMapping("/{id}/assign-role")
//    public String assignRole(@PathVariable UUID id,
//                             @RequestParam UUID roleId,
//                             RedirectAttributes redirectAttributes) {
//        try {
//            utilisateurService.assignRoleToUser(id, roleId);
//            redirectAttributes.addFlashAttribute("success", "Rôle assigné.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/admin/utilisateurs/" + id;
//    }
//
//    // Retirer un rôle
//    @PostMapping("/{id}/remove-role")
//    public String removeRole(@PathVariable UUID id,
//                             @RequestParam UUID roleId,
//                             RedirectAttributes redirectAttributes) {
//        try {
//            utilisateurService.removeRoleFromUser(id, roleId);
//            redirectAttributes.addFlashAttribute("success", "Rôle retiré.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/admin/utilisateurs/" + id;
//    }
}
