package com.klef.jfsd.sdp.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdp.model.Teacher;
import com.klef.jfsd.sdp.repository.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TeacherServiceImpl implements TeacherService {
	
	@Autowired
	private TeacherRepository teacherRepo;
	
	@Override
	public Teacher getById(int id) throws EntityNotFoundException {
		Optional<Teacher> optTeacher = teacherRepo.findById(id);
		if (optTeacher.isEmpty()) {
			throw new EntityNotFoundException("User not found!");
		}
		
		return optTeacher.get();
	}
	
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
	public String add(Teacher teacher) {
		if (teacherRepo.findById(teacher.getId()).isPresent() || teacherRepo.findByEmail(teacher.getEmail()) != null) {
			return "Teacher already exists!";
		}
		
		teacher.setPassword(hash(teacher.getPassword()));
//		teacher.setPassword(teacher.getPassword());
		teacher.setVerified(false);
		
		teacherRepo.save(teacher);
		return "Teacher added Successfully!";
	}

	@Override
	public String login(int id, String password) {
		try {
			Teacher teacher = teacherRepo.findById(id).get();
			
			if (!teacher.isVerified()) {
				return "Email not verified!";
			}
			
			if (teacher.getPassword().equals(hash(password))) {
//			if (teacher.getPassword().equals(password)) {
				return "Login Successful!";
			}
			
			return "Invalid Password!";
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return "Invalid ID!";
		}
	}
	
}
