package com.klef.jfsd.sdp.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.jfsd.sdp.dto.TeacherDTO;
import com.klef.jfsd.sdp.model.Teacher;
import com.klef.jfsd.sdp.service.TeacherServiceImpl;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
	
	@Autowired
	private TeacherServiceImpl teacherService;
	
	@GetMapping("*")
	public String pageNotFound() {
		return "<h1>404</h1><h2>Page Not Found!</h2>";
	}
	
	@GetMapping("/")
	public String index() {
		return "<h2>Teacher API Index</h2>";
	}
	
	@PostMapping("/add")
	public ResponseEntity<TeacherDTO> addTeacher(@RequestBody Teacher teacher) throws NoSuchAlgorithmException {
		String msg = teacherService.addTeacher(teacher);
		
		HttpStatus status = msg.equals("Teacher already exists!") ? HttpStatus.CONFLICT : HttpStatus.CREATED;
		
		return new ResponseEntity<>(new TeacherDTO(teacher, msg), status);
	}
	
//	@GetMapping("/forgot-password/token")
//	public ResponseEntity<String> test(@RequestParam String hashedToken) {
//		return ResponseEntity.ok(hashedToken);
////		return """
////				<html>
////				<head>
////				<style>
////					* {
////				    	font-family: monospace;
////				    	color: black;
////				    }
////				</style>
////				</head>
////				<body>
////				<h2>%s</h2>
////				<h2>Reset password</h2>
////				<form method="post" action="http://localhost:8080/api/teachers/reset-password" >
////					<label for="1" >Password:</label>
////					<input id="1" name="1" type="password" required />
////					<br /><br />
////					
////					<label for="2" >Confirm password:</label>
////					<input id="2" name="2" type="password" required />
////					<br /><br />
////					
////					<input type="submit" value="Reset password" />					
////				</form>
////				</body>
////				</html>
////				""".formatted(hashedToken);
//	}
	
	@PostMapping("/check-login")
	public ResponseEntity<TeacherDTO> checkTeacherLogin(@RequestBody Teacher teacher) {
		String msg = teacherService.checkTeacherLogin(teacher.getId(), teacher.getPassword());
		
		HttpStatus status = msg.equals("Email not verified!") ? HttpStatus.FORBIDDEN :
			(msg.equals("Login Successful!") ? HttpStatus.OK :
				(msg.equals("Invalid Password!") ? HttpStatus.UNAUTHORIZED : HttpStatus.NOT_FOUND ));
		
		return new ResponseEntity<>(new TeacherDTO(teacher, msg), status);
	}
	
}
