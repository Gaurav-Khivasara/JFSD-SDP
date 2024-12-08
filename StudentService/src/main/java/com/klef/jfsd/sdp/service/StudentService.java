package com.klef.jfsd.sdp.service;

import java.security.NoSuchAlgorithmException;

import com.klef.jfsd.sdp.model.Student;

import jakarta.persistence.EntityNotFoundException;

public interface StudentService {
	
	String add(Student student) throws NoSuchAlgorithmException;
	String login(long id, String password);
	
	Student getById(long id) throws EntityNotFoundException;

}
