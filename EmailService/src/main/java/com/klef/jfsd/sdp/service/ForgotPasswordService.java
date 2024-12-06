package com.klef.jfsd.sdp.service;

import com.klef.jfsd.sdp.model.ForgotPassword;

public interface ForgotPasswordService {
	
	String sendMail(ForgotPassword forgotPassword);
	String verifyToken(String email, String token, String password);

}
