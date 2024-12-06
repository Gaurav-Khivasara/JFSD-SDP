package com.klef.jfsd.sdp.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	
	@GetMapping("/")
	public String home() {
		return "<h2>Student Home</h2>";
	}

}
