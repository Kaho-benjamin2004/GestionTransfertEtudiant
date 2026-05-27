package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.config;

import lombok.RequiredArgsConstructor;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.JwtAuthenticationFilter;
import org.gestiontransfertetudiant.gestiontransfertetudiant.GestionUtilisateur.SERVICE.securty.jwt.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // permet @PreAuthorize sur les méthodes des contrôleurs
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter; // optionnel pour API

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF : désactivé si on utilise JWT pour API, mais pour formulaire MVC on peut le laisser activé
                // On le désactive partiellement pour les endpoints API si besoin, mais ici on garde pour les formulaires
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**")) // désactiver CSRF pour les appels API
                .authorizeHttpRequests(auth -> auth
                        // Ressources publiques
                        .antMatchers("/auth/login", "/auth/register", "/auth/forgot-password",
                                "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        // Administration centrale
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        // Agents académiques
                        .antMatchers("/agent/**").hasAnyRole("AGENT", "ADMIN")
                        // Commission pédagogique
                        .antMatchers("/commission/**").hasAnyRole("COMMISSION", "ADMIN")
                        // Universités (origine et accueil)
                        .antMatchers("/university/**").hasAnyRole("UNIV_A", "UNIV_B", "ADMIN")
                        // Étudiants
                        .antMatchers("/student/**", "/profile/**", "/sessions/**").authenticated()
                        // Dashboard accessible à tout utilisateur authentifié
                        .antMatchers("/dashboard/**").authenticated()
                        // Par défaut, tout autre endpoint nécessite authentification
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .key("uniqueAndSecretKeyForRememberMe")
                        .tokenValiditySeconds(86400) // 1 jour
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // une seule session par utilisateur
                        .expiredUrl("/auth/login?expired=true")
                );

        // Optionnel : ajouter le filtre JWT avant le filtre d'authentification par formulaire
        // pour permettre des appels API avec token. Si vous n'utilisez pas d'API REST, commentez la ligne.
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}