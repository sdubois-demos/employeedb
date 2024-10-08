package com.coresoftware.springboot.EmployeeDB.service;

import java.util.List;

import com.coresoftware.springboot.EmployeeDB.entity.Employee;

public interface EmployeeService {

	List<Employee> findAll();
	
	Employee findById(int theId);
	
	void save(Employee theEmployee);
	
	void deleteById(int theId);
	
}
