package com.coresoftware.springboot.EmployeeDB.controller;

import com.coresoftware.springboot.EmployeeDB.entity.Employee;
import com.coresoftware.springboot.EmployeeDB.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeAPIController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeAPIController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Get employees with limit, offset, and sorting
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "sort_by", required = false) String sortBy) {
        return ResponseEntity.ok(employeeService.findAll(limit, offset, sortBy));
    }

    // Bulk insert employees
    @PostMapping
    public ResponseEntity<List<Employee>> createEmployees(@RequestBody List<Employee> employees) {
        List<Employee> savedEmployees = employeeService.saveAll(employees);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployees);
    }

    // Bulk delete employees
    @DeleteMapping
    public ResponseEntity<Void> deleteAllEmployees() {
        employeeService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}