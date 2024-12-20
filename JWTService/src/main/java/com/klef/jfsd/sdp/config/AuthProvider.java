package com.klef.jfsd.sdp.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.klef.jfsd.sdp.model.User;
import com.klef.jfsd.sdp.repository.UserRepo;

@Component
public class AuthProvider implements AuthenticationProvider {
	
	@Autowired
	private UserRepo userRepo;
	
	public String hash(String str) {
		StringBuilder hashed = new StringBuilder();
		try {
			for (byte b: MessageDigest.getInstance("SHA-256").digest(str.getBytes())) {
				hashed.append(String.format("%02x", b));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hashed.toString();
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		long userId = Long.valueOf(authentication.getName());
		String password = (String) authentication.getCredentials();

		User user = userRepo.findUserByUserId(userId);
		
		if (!hash(password).equals(user.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}
		
		return new UsernamePasswordAuthenticationToken(userId, password, user.getAuthorities()); 
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
}
