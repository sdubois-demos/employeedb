package com.coresoftware.springboot.EmployeeDB.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coresoftware.springboot.EmployeeDB.dao.EmployeeRepository;
import com.coresoftware.springboot.EmployeeDB.entity.Employee;
@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository) {
		employeeRepository = theEmployeeRepository;
	}

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAllByOrderByLastNameAsc();
	}

	@Override
	public Employee findById(int theId) {
		return employeeRepository.findById(theId)
				.orElseThrow(() -> new RuntimeException("Employee not found with id: " + theId));
	}

	@Override
	public Employee save(Employee theEmployee) {
		return employeeRepository.save(theEmployee); // Now returns the saved employee
	}

	@Override
	public void deleteById(int theId) {
		employeeRepository.deleteById(theId);
	}
}





