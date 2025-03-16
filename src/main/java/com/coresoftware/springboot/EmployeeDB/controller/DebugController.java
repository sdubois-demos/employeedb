package com.coresoftware.springboot.EmployeeDB.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DebugController {
    @GetMapping("/debug")
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
}


