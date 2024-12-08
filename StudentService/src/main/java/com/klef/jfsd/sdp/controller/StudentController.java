package com.klef.jfsd.sdp.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.jfsd.sdp.dto.StudentDTO;
import com.klef.jfsd.sdp.model.Student;
import com.klef.jfsd.sdp.service.StudentServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("/api/students")
public class StudentController {
	
	@Autowired
	private StudentServiceImpl studentService;
	
	@GetMapping("")
	public String index() {
		return "<h2>Student API Index</h2>";
	}
	
	@PostMapping("/add")
	public ResponseEntity<StudentDTO> add(@RequestBody Student student) throws NoSuchAlgorithmException {
		String msg = studentService.add(student);
		
		HttpStatus status = msg.equals("Student already exists!") ? HttpStatus.CONFLICT : HttpStatus.CREATED;
		
		if (!status.equals(HttpStatus.CREATED)) {
			student = null;
		}
		
		return new ResponseEntity<>(new StudentDTO(student, msg), status);
	}
	
	@PostMapping("/login")
	public ResponseEntity<StudentDTO> login(@RequestBody Student student) {
		String msg = studentService.login(student.getId(), student.getPassword());
		
		HttpStatus status = msg.equals("Email not verified!") ? HttpStatus.FORBIDDEN :
			(msg.equals("Login Successful!") ? HttpStatus.OK :
				(msg.equals("Invalid Password!") ? HttpStatus.UNAUTHORIZED : HttpStatus.NOT_FOUND ));
		
		if (status.equals(HttpStatus.OK)) {
			student = studentService.getById(student.getId());
		}
		
		return new ResponseEntity<>(new StudentDTO(student, msg), status);
	}
	
}
