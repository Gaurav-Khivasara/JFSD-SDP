package com.klef.jfsd.sdp.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.klef.jfsd.sdp.model.Student;
import com.klef.jfsd.sdp.service.JwtUtils;
import com.klef.jfsd.sdp.service.StudentServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private StudentServiceImpl studentServiceImpl;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			Long id = jwtUtils.extractIdFromToken(token);
			
			if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				Student student = studentServiceImpl.getById(id);
				
				if (jwtUtils.validate(token, student)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							student, null, List.of(new SimpleGrantedAuthority("TEACHER")));
					
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}
		
		filterChain.doFilter(request, response);
	}

}