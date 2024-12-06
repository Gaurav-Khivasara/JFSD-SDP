package com.klef.jfsd.sdp.service;

import java.security.NoSuchAlgorithmException;

import com.klef.jfsd.sdp.model.Teacher;

public interface TeacherService {
	
	String addTeacher(Teacher teacher) throws NoSuchAlgorithmException;
	String checkTeacherLogin(int id, String password);

}
