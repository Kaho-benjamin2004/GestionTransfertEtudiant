//package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.CONTROLLEUR;
//
//import lombok.RequiredArgsConstructor;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.SERVICE.TransfertMetierImpl;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.EtudiantMetierImpl;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.UtilisateurDetailResponseDTO;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.UtilisateurService;
//import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.Collection;
//import java.util.UUID;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/dashboard")
//public class DashboardController {
//
//    private final UtilisateurService utilisateurService;
//    private final EtudiantMetierImpl etudiantMetier;
//    private final TransfertMetierImpl transfertMetier;
//
//    @GetMapping
//    public String dashboard(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() instanceof String) {
//            return "redirect:/auth/login";
//        }
//        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
//        UUID userId = userDetails.getId();
//        UtilisateurDetailResponseDTO user = utilisateurService.getUserById(userId);
//        model.addAttribute("user", user);
//
//        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
//        boolean isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
//        boolean isAgent = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_AGENT"));
//        boolean isCommission = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_COMMISSION"));
//        boolean isUniv = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_UNIV_A") || a.getAuthority().equals("ROLE_UNIV_B"));
//
//        if (isAdmin) {
//            return "dashboard/admin";
//        } else if (isAgent) {
//            return "dashboard/agent";
//        } else if (isCommission) {
//            return "dashboard/commission";
//        } else if (isUniv) {
//            return "dashboard/university";
//        } else {
//            try {
//                UUID etudiantId = etudiantMetier.getEtudiantIdByUtilisateurId(userId);
//                model.addAttribute("moyenneGenerale", etudiantMetier.calculerMoyenneGenerale(etudiantId));
//                model.addAttribute("creditsEcts", etudiantMetier.consulterCreditsValides(etudiantId));
//                model.addAttribute("demandesActives", transfertMetier.getDemandesActivesByEtudiant(etudiantId));
//
//            } catch (Exception e) {
//                // Valeurs par défaut en cas d'erreur
//                model.addAttribute("moyenneGenerale", 0);
//                model.addAttribute("creditsEcts", 0);
//                model.addAttribute("demandesActives", 0);
//            }
//            return "dashboard/student";
//        }
//    }
//}
package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.CONTROLLEUR;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.DemandeTransfert.SERVICE.ITransfertMetier;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionEtudiant.SERVICE.IEtudiantMetier;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.DAO.dto.response.UtilisateurDetailResponseDTO;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.UtilisateurService;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final UtilisateurService utilisateurService;
    private final IEtudiantMetier etudiantMetier;      // interface du module 2
    private final ITransfertMetier transfertMetier;    // interface du module 3

    @GetMapping
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() instanceof String) {
            return "redirect:/auth/login";
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        UUID userId = userDetails.getId();
        UtilisateurDetailResponseDTO user = utilisateurService.getUserById(userId);
        model.addAttribute("user", user);

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        boolean isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isAgent = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_AGENT"));
        boolean isCommission = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_COMMISSION"));
        boolean isUniv = authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_UNIV_A") || a.getAuthority().equals("ROLE_UNIV_B"));

        if (isAdmin) {
            return "dashboard/admin";
        } else if (isAgent) {
            return "dashboard/agent";
        } else if (isCommission) {
            return "dashboard/commission";
        } else if (isUniv) {
            return "dashboard/university";
        } else {
            // Rôle étudiant
            try {
                UUID etudiantId = etudiantMetier.getEtudiantIdByUtilisateurId(userId);
                // Moyenne générale
                BigDecimal moyenneGenerale = etudiantMetier.calculerMoyenneGenerale(etudiantId);
                model.addAttribute("moyenneGenerale", moyenneGenerale != null ? moyenneGenerale : BigDecimal.ZERO);

                // Crédits ECTS acquis (somme des crédits validés)
                int creditsAcquis = etudiantMetier.consulterCreditsValides(etudiantId).stream()
                        .mapToInt(c -> c.getNombre() != null ? c.getNombre() : 0)
                        .sum();
                int creditsTotal = 180; // objectif total (à ajuster selon votre référentiel)
                model.addAttribute("creditsAcquis", creditsAcquis);
                model.addAttribute("creditsRestants", creditsTotal - creditsAcquis);
                model.addAttribute("creditsEcts", creditsAcquis); // pour la carte simple

                // Demandes actives
                long demandesActives = transfertMetier.getDemandesActivesByEtudiant(etudiantId);
                model.addAttribute("demandesActives", demandesActives);

                // Moyennes par semestre (ou par année) – à implémenter dans IEtudiantMetier
//                List<BigDecimal> moyennesParSemestre = etudiantMetier.getMoyennesParSemestre(etudiantId);
                List<BigDecimal> moyennes = etudiantMetier.getMoyennesParSemestre(etudiantId);
                List<BigDecimal> moyennesFinal = new ArrayList<>(moyennes);
// Compléter avec des zéros pour avoir 6 valeurs (S1..S6)
                while (moyennesFinal.size() < 6) {
                    moyennesFinal.add(BigDecimal.ZERO);
                }
                model.addAttribute("moyennesParSemestre", moyennesFinal);

            } catch (Exception e) {
                // Valeurs par défaut en cas d'erreur (évite d'afficher des valeurs statiques)
                model.addAttribute("moyenneGenerale", BigDecimal.ZERO);
                model.addAttribute("creditsAcquis", 100);
                model.addAttribute("creditsRestants", 180);
                model.addAttribute("creditsEcts", 0);
                model.addAttribute("demandesActives", 0);
                model.addAttribute("moyennesParSemestre", List.of(0, 0, 0, 0, 0, 0));
            }
            return "dashboard/student";
        }
    }
}