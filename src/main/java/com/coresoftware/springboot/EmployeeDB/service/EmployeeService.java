package com.coresoftware.springboot.EmployeeDB.service;

import java.util.List;
import com.coresoftware.springboot.EmployeeDB.entity.Employee;

public interface EmployeeService {
	List<Employee> findAll(Integer limit, Integer offset, String sortBy);
	Employee findById(int theId);
	Employee save(Employee theEmployee);
	List<Employee> saveAll(List<Employee> employees); // Bulk insert support
	void deleteById(int theId);
	void deleteAll(); // Bulk delete support
}