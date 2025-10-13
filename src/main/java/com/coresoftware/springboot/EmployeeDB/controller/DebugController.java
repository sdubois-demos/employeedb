package com.coresoftware.springboot.EmployeeDB.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController

public class DebugController {
    @GetMapping("/debug/header")
    public Map<String, String> getDebug(HttpServletRequest request) {
        return getHttpHeaders(request);
    }

    private Map<String, String> getHttpHeaders(HttpServletRequest request) {
        Map<String, String> headersMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headersMap.put(headerName, headerValue);
        }
        return headersMap;
    }

    @GetMapping("/debug/auth")
    public Map<String, Object> getAuthInfo(HttpServletRequest request, Authentication authentication) {
        Map<String, Object> out = new HashMap<>();

        if (request.getSession(false) != null) {
            out.put("sessionId", request.getSession(false).getId());
        }

        if (authentication != null) {
            out.put("authenticated", authentication.isAuthenticated());
            out.put("principalClass", authentication.getPrincipal().getClass().getName());
            out.put("name", authentication.getName());
            out.put("authorities",
                    authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

            Object principal = authentication.getPrincipal();
            if (principal instanceof Saml2AuthenticatedPrincipal saml) {
                out.put("samlNameId", saml.getName());
                out.put("attributes", saml.getAttributes()); // all IdP-asserted attributes
            }
        } else {
            out.put("authenticated", false);
        }
        return out;
    }

}


