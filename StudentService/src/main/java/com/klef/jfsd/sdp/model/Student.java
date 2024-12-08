package com.klef.jfsd.sdp.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
	@SequenceGenerator(name = "student_seq", sequenceName = "student_id_seq", initialValue = 1000000001, allocationSize = 1)
	private long id;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private boolean isVerified;
	
	public Student() { }

	public Student(Student student) {
		this.id = student.id;
		this.firstName = student.firstName;
		this.lastName = student.lastName;
		this.email = student.email;
		this.password = student.password;
		this.isVerified = student.isVerified;
	}

	public Student(long id, String firstName, String lastName, String email, String password, boolean isVerified) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.isVerified = isVerified;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("STUDENT"));
	}

	@Override
	public String toString() {
		return "id = " + id + "\nfirstName = " + firstName + "\nlastName = " + lastName + "\nemail = " + email
				+ "\npassword = " + password + "\nisVerified = " + isVerified;
	}

}
