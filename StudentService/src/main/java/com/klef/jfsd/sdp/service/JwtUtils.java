package com.klef.jfsd.sdp.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdp.model.Student;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtils {
	
	private final String secretKey = "jwtSecretKey1234jwtSecretKey1234jwtSecretKey1234";
	
	private Claims extractAllClaimsFromToken(String token) {
		return Jwts.parser()
				.verifyWith(key())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public Long extractIdFromToken(String token) {
		return Long.valueOf(extractAllClaimsFromToken(token).getSubject());
	}
	
	public boolean validate(String token, Student student) {
		return (extractIdFromToken(token) == student.getId()
				&& !extractAllClaimsFromToken(token).getExpiration().before(new Date()));
	}
	
	private SecretKey key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
	}

}
