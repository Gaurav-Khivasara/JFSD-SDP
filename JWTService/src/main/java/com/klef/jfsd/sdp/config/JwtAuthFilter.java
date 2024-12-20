package com.klef.jfsd.sdp.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.klef.jfsd.sdp.model.User;
import com.klef.jfsd.sdp.service.JwtUtils;
import com.klef.jfsd.sdp.service.UserDetailsImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsImpl userDetailsImpl;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		
		if (authHeader != null && authHeader.startsWith("Bearer")) {
			String token = authHeader.substring(7);
			// TODO
//			String username = jwtUtils.extractUsername(token);
			Long userId = jwtUtils.extractUserIdFromToken(token);
			
			if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//				User user = userDetailsImpl.loadUserByUsername(username);
				User user = userDetailsImpl.getUserByUserId(userId);
				
				if (jwtUtils.validate(token, user)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							user, null, user.getAuthorities());
					
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
