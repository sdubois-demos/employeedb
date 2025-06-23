package com.coresoftware.springboot.EmployeeDB.service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.coresoftware.springboot.EmployeeDB.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coresoftware.springboot.EmployeeDB.dao.EmployeeRepository;
import com.coresoftware.springboot.EmployeeDB.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public List<Employee> findAll(Integer limit, Integer offset, String sortBy) {
		if (limit == null || limit <= 0) {
			limit = Integer.MAX_VALUE; // Fetch all if no limit specified
		}
		if (offset == null || offset < 0) {
			offset = 0; // Default offset to 0
		}

		// Map valid column names to actual entity field names
		Map<String, String> validSortFields = new HashMap<>();
		validSortFields.put("id", "id");
		validSortFields.put("email", "email");
		validSortFields.put("first_name", "firstName");
		validSortFields.put("last_name", "lastName");

		// Determine sort direction
		boolean descending = sortBy != null && sortBy.startsWith("-");
		String sortKey = (sortBy != null) ? sortBy.replace("-", "") : "last_name";
		String sortField = validSortFields.getOrDefault(sortKey, "lastName");

		Sort.Direction direction = descending ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by(direction, sortField));

		Page<Employee> page = employeeRepository.findAll(pageable);
		return page.getContent(); // Extract the list of employees
	}

	/*
	public List<Employee> findAll(Integer limit, Integer offset, String sortBy) {
		if (limit == null || limit <= 0) {
			limit = Integer.MAX_VALUE; // Fetch all if no limit specified
		}
		if (offset == null || offset < 0) {
			offset = 0; // Default offset to 0
		}

		// Map valid column names to actual entity field names
		Map<String, String> validSortFields = new HashMap<>();
		validSortFields.put("id", "id");
		validSortFields.put("email", "email");
		validSortFields.put("first_name", "firstName");
		validSortFields.put("last_name", "lastName");

		// Default sorting by last name if sort_by is invalid
		String sortField = validSortFields.getOrDefault(sortBy, "lastName");

		Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by(Sort.Direction.ASC, sortField));

		Page<Employee> page = employeeRepository.findAll(pageable);

		return page.getContent(); // Extract the list of employees
	}
	 */

	@Override
	public Employee findById(int theId) {
		return employeeRepository.findById(theId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + theId));
	}

	@Override
	public Employee save(Employee theEmployee) {
		return employeeRepository.save(theEmployee);
	}

	@Override
	public List<Employee> saveAll(List<Employee> employees) {
		return employeeRepository.saveAll(employees); // Bulk insert
	}

	@Override
	public void deleteById(int theId) {
		employeeRepository.deleteById(theId);
	}

	@Override
	public void deleteAll() {
		employeeRepository.deleteAll(); // Bulk delete
	}
}