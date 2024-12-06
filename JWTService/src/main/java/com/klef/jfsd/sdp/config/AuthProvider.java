package com.klef.jfsd.sdp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.klef.jfsd.sdp.model.User;
import com.klef.jfsd.sdp.repository.UserRepo;

@Component
public class AuthProvider implements AuthenticationProvider {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		long userId = Long.valueOf(authentication.getName());
		String password = (String) authentication.getCredentials();
		
		User user = userRepo.findUserByUserId(userId);
		
		if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}
		
		return new UsernamePasswordAuthenticationToken(userId, password, user.getAuthorities()); 
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
}
