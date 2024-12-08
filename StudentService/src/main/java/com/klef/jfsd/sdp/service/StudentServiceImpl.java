package com.klef.jfsd.sdp.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdp.model.Student;
import com.klef.jfsd.sdp.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Override
	public Student getById(long id) throws EntityNotFoundException {
		Optional<Student> optStudent = studentRepo.findById(id);
		if (optStudent.isEmpty()) {
			throw new EntityNotFoundException("User not found!");
		}
		
		return optStudent.get();
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
	public String add(Student student) {
		if (studentRepo.findById(student.getId()).isPresent() || studentRepo.findByEmail(student.getEmail()) != null) {
			return "Student already exists!";
		}
		
		student.setPassword(hash(student.getPassword()));
//		student.setPassword(student.getPassword());
		student.setVerified(false);
		
		studentRepo.save(student);
		return "Student added Successfully!";
	}

	@Override
	public String login(long id, String password) throws EntityNotFoundException {
		try {
			Student student = studentRepo.findById(id).get();
			
			if (!student.isVerified()) {
				return "Email not verified!";
			}
			
			if (student.getPassword().equals(hash(password))) {
//			if (student.getPassword().equals(password)) {
				return "Login Successful!";
			}
			
			return "Invalid Password!";
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return "Invalid ID!";
		}
	}
	
}
