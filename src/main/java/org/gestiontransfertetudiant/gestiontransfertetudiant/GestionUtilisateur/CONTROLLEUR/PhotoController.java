//package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.CONTROLLEUR;
//
//import lombok.RequiredArgsConstructor;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.UtilisateurService;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//@RequestMapping("/utilisateur/photo")
//@RequiredArgsConstructor
//public class PhotoController {
//
//    private final UtilisateurService utilisateurService;
//
//    @PostMapping("/profil")
//    public String uploadPhotoProfil(@RequestParam("file") MultipartFile file,
//                                    RedirectAttributes redirectAttributes,
//                                    Authentication auth) {
//        // Récupérer l'utilisateur connecté
//        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
//        try {
//            String photoUrl = utilisateurService.savePhotoProfil(userDetails.getId(), file);
//            redirectAttributes.addFlashAttribute("success", "Photo de profil mise à jour.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/etudiant/profil/view";
//    }
//
//    @PostMapping("/couverture")
//    public String uploadPhotoCouverture(@RequestParam("file") MultipartFile file,
//                                        RedirectAttributes redirectAttributes,
//                                        Authentication auth) {
//        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
//        try {
//            String photoUrl = utilisateurService.savePhotoCouverture(userDetails.getId(), file);
//            redirectAttributes.addFlashAttribute("success", "Photo de couverture mise à jour.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//        return "redirect:/etudiant/profil/view";
//    }
//}
package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.CONTROLLEUR;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.UtilisateurService;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/utilisateur/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final UtilisateurService utilisateurService;

    @PostMapping("/profil")
    public String uploadPhotoProfil(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes,
                                    Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        try {
            String photoUrl = utilisateurService.savePhotoProfil(userDetails.getId(), file);
            redirectAttributes.addFlashAttribute("success", "Photo de profil mise à jour.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/etudiant/profil/view";
    }

    @PostMapping("/couverture")
    public String uploadPhotoCouverture(@RequestParam("file") MultipartFile file,
                                        RedirectAttributes redirectAttributes,
                                        Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        try {
            String photoUrl = utilisateurService.savePhotoCouverture(userDetails.getId(), file);
            redirectAttributes.addFlashAttribute("success", "Photo de couverture mise à jour.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/etudiant/profil/view";
    }
}