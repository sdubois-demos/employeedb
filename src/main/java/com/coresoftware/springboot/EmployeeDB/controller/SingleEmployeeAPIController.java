package com.coresoftware.springboot.EmployeeDB.controller;

import com.coresoftware.springboot.EmployeeDB.entity.Employee;
import com.coresoftware.springboot.EmployeeDB.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class SingleEmployeeAPIController {

    private final EmployeeService employeeService;

    @Autowired
    public SingleEmployeeAPIController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Get employee by ID (path)
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }

    // Get employee by ID (query)
    @GetMapping
    public ResponseEntity<Employee> getEmployeeByIdQuery(@RequestParam("id") int id) {
        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }

    // Create a single employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    // Update employee by ID (path)
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable int id,
            @RequestBody Employee employeeDetails) {
        Employee existingEmployee = employeeService.findById(id);
        existingEmployee.setFirstName(employeeDetails.getFirstName());
        existingEmployee.setLastName(employeeDetails.getLastName());
        existingEmployee.setEmail(employeeDetails.getEmail());
        return ResponseEntity.ok(employeeService.save(existingEmployee));
    }

    // Update employee by ID (query)
    @PutMapping
    public ResponseEntity<Employee> updateEmployeeQuery(
            @RequestParam("id") int id,
            @RequestBody Employee employeeDetails) {
        Employee existingEmployee = employeeService.findById(id);
        existingEmployee.setFirstName(employeeDetails.getFirstName());
        existingEmployee.setLastName(employeeDetails.getLastName());
        existingEmployee.setEmail(employeeDetails.getEmail());
        return ResponseEntity.ok(employeeService.save(existingEmployee));
    }

    // Delete employee by ID (path)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Delete employee by ID (query)
    @DeleteMapping
    public ResponseEntity<Void> deleteEmployeeQuery(@RequestParam("id") int id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}