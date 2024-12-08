package com.klef.jfsd.sdp.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdp.model.Teacher;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtils {
	
	@Value("${spring.application.security.jwt.secretKey}")
	private String secretKey;
	
	private Claims extractAllClaimsFromToken(String token) {
		return Jwts.parser()
				.verifyWith(key())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public Integer extractIdFromToken(String token) {
		return Integer.valueOf(extractAllClaimsFromToken(token).getSubject());
	}
	
	public boolean validate(String token, Teacher teacher) {
		return (extractIdFromToken(token) == teacher.getId()
				&& !extractAllClaimsFromToken(token).getExpiration().before(new Date()));
	}
	
	private SecretKey key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
	}

}
