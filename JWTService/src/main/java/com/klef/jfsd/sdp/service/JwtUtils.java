package com.klef.jfsd.sdp.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdp.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtils {
	
	@Value("${spring.application.security.jwt.secretKey}")
	private String secretKey;
	
	private Claims extractAllExclaimsFromToken(String token) {
		return Jwts.parser()
				.verifyWith(key())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	// TODO
//	public String extractUsername(String token) {
//		return extractAllExclaims(token).getSubject();
//	}
	public Long extractUserIdFromToken(String token) {
		return Long.valueOf(extractAllExclaimsFromToken(token).getSubject());
	}
	
	public boolean validate(String token, User user) {
		// TODO
//		return (extractUsername(token).equals(user.getUsername())
//				&& !extractAllExclaimsFromToken(token).getExpiration().before(new Date()));
		return (extractUserIdFromToken(token) == user.getUserId()
				&& !extractAllExclaimsFromToken(token).getExpiration().before(new Date()));
	}
	
	public String generateToken(User user) {
		return Jwts.builder()
				// TODO
//				.subject(user.getUsername())
				.subject(Long.toString(user.getUserId()))
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1day = 1hr * 24
				.signWith(key())
				.compact();
	}
	
	private SecretKey key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
	}

}
