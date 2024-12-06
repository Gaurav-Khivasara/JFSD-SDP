package com.klef.jfsd.sdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.jfsd.sdp.model.User;
import com.klef.jfsd.sdp.service.AuthService;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody User userReq) {
		return ResponseEntity.ok(authService.singup(userReq));
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User userReq) {
		return ResponseEntity.ok(authService.authenticate(userReq));
	}
	
	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("<h2>You are Authenticated!</h2>");
	}

}
