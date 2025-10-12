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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${edb.authentication:none}")
    private String mode;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/css/**", "/js/**", "/images/**",
                                "/actuator/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
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
                http
                        .saml2Login(Customizer.withDefaults())     // /saml2/authenticate/{registrationId}
                        .logout(l -> l.logoutSuccessUrl("/"));
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
