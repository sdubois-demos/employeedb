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

    // Get all employees (sorted by last name)
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }

    // Create new employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    // Update existing employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable int id,
            @RequestBody Employee employeeDetails) {
        Employee existingEmployee = employeeService.findById(id);
        existingEmployee.setFirstName(employeeDetails.getFirstName());
        existingEmployee.setLastName(employeeDetails.getLastName());
        existingEmployee.setEmail(employeeDetails.getEmail());
        Employee updatedEmployee = employeeService.save(existingEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}