package com.coresoftware.springboot.EmployeeDB.controller;

import com.coresoftware.springboot.EmployeeDB.entity.Employee;
import com.coresoftware.springboot.EmployeeDB.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService theEmployeeService) {
		this.employeeService = theEmployeeService;
	}

	// Add mapping for "/list" with limit, offset, and sorting
	@GetMapping("/list")
	public String listEmployees(
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "sort_by", required = false) String sortBy,
			Model theModel, HttpServletRequest request) {
		String theUplinkURL = request.getRequestURL().toString();
		String theLoadBalancerIp = request.getHeader("X-Forwarded-For");

		// Get the employees from db with limit, offset, and sorting
		List<Employee> theEmployees = employeeService.findAll(limit, offset, sortBy);

		// Add to the Spring model
		theModel.addAttribute("employees", theEmployees);
		theModel.addAttribute("uplinkURL", theUplinkURL);
		theModel.addAttribute("loadBalancerIp", theLoadBalancerIp);

		return "employees/list-employees";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Employee theEmployee = new Employee();
		theModel.addAttribute("employee", theEmployee);
		return "employees/employee-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {
		Employee theEmployee = employeeService.findById(theId);
		theModel.addAttribute("employee", theEmployee);
		return "employees/employee-form";
	}

	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {
		employeeService.save(theEmployee);
		return "redirect:/employees/list";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {
		employeeService.deleteById(theId);
		return "redirect:/employees/list";
	}
}