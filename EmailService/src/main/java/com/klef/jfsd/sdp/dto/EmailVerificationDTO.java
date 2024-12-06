package com.klef.jfsd.sdp.dto;

import com.klef.jfsd.sdp.model.EmailVerification;

public class EmailVerificationDTO {
	
	private EmailVerification emailVerification;
	private String msg;
	
	public EmailVerificationDTO() { }

	public EmailVerificationDTO(EmailVerification emailVerification, String msg) {
		this.emailVerification = emailVerification;
		this.msg = msg;
	}

	public EmailVerification getEmailVerification() {
		return emailVerification;
	}

	public void setEmailVerification(EmailVerification emailVerification) {
		this.emailVerification = emailVerification;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
