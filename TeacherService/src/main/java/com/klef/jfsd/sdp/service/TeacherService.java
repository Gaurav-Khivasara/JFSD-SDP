package com.klef.jfsd.sdp.service;

import java.security.NoSuchAlgorithmException;

import com.klef.jfsd.sdp.model.Teacher;

import jakarta.persistence.EntityNotFoundException;

public interface TeacherService {
	
	String add(Teacher teacher) throws NoSuchAlgorithmException;
	String login(int id, String password);
	
	Teacher getById(int id) throws EntityNotFoundException;

}
