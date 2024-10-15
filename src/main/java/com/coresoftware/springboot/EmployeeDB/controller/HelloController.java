package com.coresoftware.springboot.EmployeeDB.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	// create a mapping for "/hello"
	@GetMapping("/hello")
	public String sayDebug(Model theModel) {

		theModel.addAttribute("theDate", new java.util.Date());

		return "helloworld";
	}
}








