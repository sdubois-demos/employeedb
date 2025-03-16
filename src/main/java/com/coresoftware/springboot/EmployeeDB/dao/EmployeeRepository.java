package com.coresoftware.springboot.EmployeeDB.dao;

import com.coresoftware.springboot.EmployeeDB.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Method to retrieve employees sorted dynamically with pagination
    Page<Employee> findAll(Pageable pageable);
}