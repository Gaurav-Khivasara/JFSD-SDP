package com.klef.jfsd.sdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdp.model.User;
import com.klef.jfsd.sdp.repository.UserRepo;

@Service
public class AuthService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authManager;
	
	public String singup(User userReq) {
		User user = new User();
		user.setEmail(userReq.getEmail());
		user.setPassword(passwordEncoder.encode(userReq.getPassword()));
		user.setRole(userReq.getRole());
		user.setUserId(userReq.getUserId());
		// TODO
//		user.setUsername(userReq.getUsername());
		userRepo.save(user);
		
		return "Successfully signed up!";
	}
	
	public String authenticate(User userReq) {
		// TODO
//		authManager.authenticate(new UsernamePasswordAuthenticationToken(userReq.getUsername(), userReq.getPassword()));
		authManager.authenticate(new UsernamePasswordAuthenticationToken(userReq.getUserId(), userReq.getPassword()));
		
		// TODO
//		User user = userRepo.findByUsername(userReq.getUsername());
		User user = userRepo.findUserByUserId(userReq.getUserId());
		return jwtUtils.generateToken(user);
	}
	
}
