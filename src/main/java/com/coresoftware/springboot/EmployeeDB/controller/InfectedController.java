package com.coresoftware.springboot.EmployeeDB.controller;

import com.coresoftware.springboot.EmployeeDB.entity.Employee;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;  // Updated import
import java.util.HashMap;
import java.util.Map;
import java.util.Enumeration;  // Import Enumeration

@Controller
@RequestMapping("/infected")
public class InfectedController {

    @GetMapping("/tumorGrowth")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        //Employee theEmployee = new Employee();
        //theModel.addAttribute("employee", theEmployee);

        return "infected/infected-form";
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> receiveMalignancy(
            @RequestParam(required = false) String input,
            HttpServletRequest request) {

        // Create a map to hold the response data
        Map<String, Object> response = new HashMap<>();

        // Get headers from the request
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.add(headerName, request.getHeader(headerName));
        }

        // Prepare the response data
        response.put("payload", input);
        response.put("headers", headers);
        response.put("requestURI", request.getRequestURI());
        response.put("method", request.getMethod());

        // Return the response as JSON
        return ResponseEntity.ok(response);
    }
}