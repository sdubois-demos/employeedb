package com.coresoftware.springboot.EmployeeDB.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.ldap.authentication.LdapBindAuthenticationManagerFactory;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.web.SecurityFilterChain;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${edb.authentication:none}")
    private String mode;

    @Value("${edb.saml.idp-logout-url:https://fortiauth.fortidemo.ch/saml-idp/employeedb/logout/}")
    private String idpLogoutUrl;

    @Value("${edb.server-base-url:http://localhost:8080}")
    private String serverBaseUrl;

    @Value("${edb.saml.post-logout-redirect:/}")
    private String postLogoutRedirect;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Public pages & assets
                        .requestMatchers("/", "/index.html",
                                "/css/**", "/js/**", "/images/**", "/webjars/**",
                                // Observability & debug
                                "/actuator/**", "/debug/**",
                                // Public list page should not force SSO
                                "/employees/list",
                                // SAML endpoints + metadata + logout endpoint itself
                                "/saml2/authenticate/**", "/saml2/service-provider-metadata/**",
                                "/logout", "/logout/saml2/slo", "/logout/saml2/slo/**").permitAll()
                        // Protect anything that mutates data
                        .requestMatchers("/employees/showFormForAdd", "/employees/save",
                                "/employees/delete/**", "/employees/updateForm/**").authenticated()
                        // Default: anything else is allowed (keeps UX open unless you add new admin paths)
                        .anyRequest().permitAll()
                );

        switch (mode) {
            case "none" -> {
                // No external provider, simple form login to guard endpoints (in-memory anonymous)
                http.formLogin(Customizer.withDefaults())
                        .logout(l -> l.logoutSuccessUrl("/"));
            }
            case "ldap" -> {
                http.formLogin(Customizer.withDefaults())
                        .logout(l -> l.logoutSuccessUrl("/"));
                // AuthN is provided by the LDAP AuthenticationManager bean below
            }
            case "saml" -> {
                // Keep SAML2 login
                http.saml2Login(Customizer.withDefaults());

                // Manual RP logout fallback:
                //  - Invalidate local SP session
                //  - Then redirect browser to IdP SLO endpoint (FAC tenant)
                http.logout(l -> l
                        .logoutUrl("/logout/saml2/slo")        // Sign out link points here
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Build RelayState target back to the app (serverBaseUrl + postLogoutRedirect)
                            String target = serverBaseUrl.endsWith("/")
                                    ? serverBaseUrl + (postLogoutRedirect.startsWith("/") ? postLogoutRedirect.substring(1) : postLogoutRedirect)
                                    : serverBaseUrl + (postLogoutRedirect.startsWith("/") ? postLogoutRedirect : ("/" + postLogoutRedirect));

                            String redirect = idpLogoutUrl
                                    + (idpLogoutUrl.contains("?") ? "&" : "?")
                                    + "RelayState="
                                    + URLEncoder.encode(target, StandardCharsets.UTF_8);

                            response.sendRedirect(redirect);
                        })
                );
            }
            default -> throw new IllegalStateException("Unknown edb.authentication mode: " + mode);
        }

        return http.build();
    }

    // ---------- LDAP beans (only created if mode=ldap) ----------
    @Bean
    @ConditionalOnProperty(prefix = "edb", name = "authentication", havingValue = "ldap")
    LdapContextSource ldapContextSource(
            @Value("${edb.ldap.url}") String url,
            @Value("${edb.ldap.base}") String base,
            @Value("${edb.ldap.managerDn}") String managerDn,
            @Value("${edb.ldap.managerPassword}") String managerPassword
    ) {
        LdapContextSource ctx = new LdapContextSource();
        ctx.setUrl(url);
        ctx.setBase(base);
        ctx.setUserDn(managerDn);
        ctx.setPassword(managerPassword);
        ctx.afterPropertiesSet();
        return ctx;
    }


    @Bean
    @ConditionalOnProperty(prefix = "edb", name = "authentication", havingValue = "ldap")
    AuthenticationManager ldapAuthenticationManager(
            LdapContextSource contextSource,
            @Value("${edb.ldap.user-dn-pattern:}") String userDnPattern,
            @Value("${edb.ldap.userSearchBase:}") String userSearchBase,
            @Value("${edb.ldap.userSearchFilter:(uid={0})}") String userSearchFilter
    ) {
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);

        if (!userDnPattern.isBlank()) {
            factory.setUserDnPatterns(userDnPattern);
        } else if (!userSearchBase.isBlank()) {
            factory.setUserSearchBase(userSearchBase);
            factory.setUserSearchFilter(userSearchFilter);
        }
        return factory.createAuthenticationManager();
    }


    // For SAML, Spring Boot auto-configures RelyingPartyRegistrationRepository from properties.
}
