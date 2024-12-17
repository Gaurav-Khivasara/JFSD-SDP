package com.klef.jfsd.sdp.config;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.klef.jfsd.sdp.model.Student;
import com.klef.jfsd.sdp.repository.StudentRepository;
import com.klef.jfsd.sdp.service.StudentServiceImpl;

@Component
public class AuthProvider implements AuthenticationProvider {
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private StudentServiceImpl studentServiceImpl;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		long id = Long.valueOf(authentication.getName());
		String password = (String) authentication.getCredentials();
		
		Optional<Student> optStudent = studentRepo.findById(id);
		if (optStudent.isEmpty()) {
			throw new BadCredentialsException("User not found!");
		}
		
		if (studentServiceImpl.hash(password).equals( optStudent.get().getPassword())) {
			throw new BadCredentialsException("Invalid password!");
		} 
		
		return new UsernamePasswordAuthenticationToken(id, password, List.of(new SimpleGrantedAuthority("STUDENT")));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
