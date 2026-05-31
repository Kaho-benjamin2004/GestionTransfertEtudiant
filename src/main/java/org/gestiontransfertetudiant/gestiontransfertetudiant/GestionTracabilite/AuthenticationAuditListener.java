package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionTracabilite;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionTracabilite.SERVICE.AuditService;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationAuditListener {

    private final AuditService auditService;

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        String login = event.getAuthentication().getName();
        String ip = "";
        if (event.getAuthentication().getDetails() instanceof WebAuthenticationDetails) {
            ip = ((WebAuthenticationDetails) event.getAuthentication().getDetails()).getRemoteAddress();
        }
        auditService.enregistrerAction(
                "LOGIN_SUCCESS", "Authentication", null,
                "Connexion réussie", login, ip
        );
    }
}