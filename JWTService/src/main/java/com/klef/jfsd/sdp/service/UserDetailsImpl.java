package com.klef.jfsd.sdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdp.model.User;
import com.klef.jfsd.sdp.repository.UserRepo;

@Service
//public class UserDetailsImpl implements UserDetailsService {
public class UserDetailsImpl {
	
	@Autowired
	private UserRepo userRepo;

	// TODO
//	@Override
//	public User loadUserByUsername(String username) throws UsernameNotFoundException {
//		return userRepo.findByUsername(username);
//	}
	public User getUserByUserId(long userId) {
		return userRepo.findUserByUserId(userId);
	}
	
}
