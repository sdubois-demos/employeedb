package com.coresoftware.springboot.EmployeeDB.controller;

import com.coresoftware.springboot.EmployeeDB.dao.EmployeeRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class EmployeeCountHealthIndicator implements HealthIndicator {

    private final EmployeeRepository employeeRepository;

    public EmployeeCountHealthIndicator(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Health health() {
        long count = employeeRepository.count();
        return Health.up()
                .withDetail("employeeCount", count)
                .withDetail("message", "EmployeeDB is operational")
                .build();
    }
}