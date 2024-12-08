package com.klef.jfsd.sdp.dto;

import com.klef.jfsd.sdp.model.Student;

public class StudentDTO {
	
	private Student student;
	private String msg;
	
	public StudentDTO() { }
	
	public StudentDTO(Student student, String msg) {
		this.student = student;
		this.msg = msg;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
