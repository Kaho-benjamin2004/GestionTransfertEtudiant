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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    // private final JwtAuthenticationFilter jwtAuthenticationFilter; // Inutile pour les vues MVC, à commenter si pas d'API

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
                // Désactiver CSRF pour éviter les erreurs (développement) – à réactiver en production si besoin
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Ressources publiques
                        .requestMatchers("/auth/login", "/auth/register","/uploads/**" ,"/auth/forgot-password", "/auth/reset-password",
                                "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        // Administration centrale
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Agents académiques
                        .requestMatchers("/agent/**").hasAnyRole("AGENT", "ADMIN")
                        // Commission pédagogique
                        .requestMatchers("/commission/**").hasAnyRole("COMMISSION", "ADMIN")
                        // Universités (origine et accueil)
                        .requestMatchers("/university/**").hasAnyRole("UNIV_A", "UNIV_B", "ADMIN")
                        .requestMatchers("/uploads/**").permitAll()
                        // Étudiants
                        .requestMatchers("/etudiant/**", "/utilisateur/photo/**","/profile/**", "/etudiant/transferts/**","/sessions/**").authenticated()
                        // Dashboard accessible à tout utilisateur authentifié
                        .requestMatchers("/dashboard/**").authenticated()
                        // Toute autre requête nécessite authentification
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
                        .tokenValiditySeconds(86400)
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .expiredUrl("/auth/login?expired=true")
                );

        // Si vous utilisez JWT pour des API REST, vous pouvez ajouter le filtre mais il faudrait le configurer
        // pour ignorer les requêtes vers les endpoints MVC. Pour l'instant, nous le commentons.
        // http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}