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
            @RequestParam(value = "sort_by", required = false) String sortBy,
            @RequestHeader(value = "debug", required = false) Boolean debug,
            @RequestHeader(value = "size", required = false) Integer size) {
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

    // Get a single employee by query parameter
    @GetMapping("/employee")
    public ResponseEntity<Employee> getEmployeeByIdQuery(@RequestParam("id") int id) {
        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }

    // Update an employee using query parameter
    @PutMapping("/employee")
    public ResponseEntity<Employee> updateEmployeeByQuery(@RequestParam("id") int id, @RequestBody Employee updatedEmployee) {
        Employee existing = employeeService.findById(id);
        existing.setFirstName(updatedEmployee.getFirstName());
        existing.setLastName(updatedEmployee.getLastName());
        existing.setEmail(updatedEmployee.getEmail());
        return ResponseEntity.ok(employeeService.save(existing));
    }

    // Delete an employee using query parameter
    @DeleteMapping("/employee")
    public ResponseEntity<Void> deleteEmployeeByQuery(@RequestParam("id") int id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}