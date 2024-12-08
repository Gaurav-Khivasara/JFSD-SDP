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

import com.klef.jfsd.sdp.model.Teacher;
import com.klef.jfsd.sdp.repository.TeacherRepository;
import com.klef.jfsd.sdp.service.TeacherServiceImpl;

@Component
public class AuthProvider implements AuthenticationProvider {
	
	@Autowired
	private TeacherRepository teacherRepo;
	
	@Autowired
	private TeacherServiceImpl teacherServiceImpl;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		int id = Integer.valueOf(authentication.getName());
		String password = (String) authentication.getCredentials();
		
		Optional<Teacher> optTeacher = teacherRepo.findById(id);
		if (optTeacher.isEmpty()) {
			throw new BadCredentialsException("User not found!");
		}
		
		if (teacherServiceImpl.hash(password).equals( optTeacher.get().getPassword())) {
			throw new BadCredentialsException("Invalid password!");
		} 
		
		return new UsernamePasswordAuthenticationToken(id, password, List.of(new SimpleGrantedAuthority("TEACHER")));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
