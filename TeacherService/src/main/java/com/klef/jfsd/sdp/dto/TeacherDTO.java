package com.klef.jfsd.sdp.dto;

import com.klef.jfsd.sdp.model.Teacher;

public class TeacherDTO {
	
	private Teacher teacher;
	private String msg;
	
	public TeacherDTO() { }
	
	public TeacherDTO(Teacher teacher, String msg) {
		this.teacher = teacher;
		this.msg = msg;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
