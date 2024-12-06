package com.klef.jfsd.sdp.dto;

import com.klef.jfsd.sdp.model.ForgotPassword;

public class ForgotPasswordDTO {
	
	private ForgotPassword forgotPassword;
	private String msg;
	
	public ForgotPasswordDTO() { }
	
	public ForgotPasswordDTO(ForgotPassword forgotPassword, String msg) {
		this.forgotPassword = forgotPassword;
		this.msg = msg;
	}

	public ForgotPassword getForgotPassword() {
		return forgotPassword;
	}

	public void setForgotPassword(ForgotPassword forgotPassword) {
		this.forgotPassword = forgotPassword;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
