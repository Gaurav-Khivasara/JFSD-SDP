package com.klef.jfsd.sdp.service;

import com.klef.jfsd.sdp.model.EmailVerification;

public interface EmailVerificationService {
	
	String sendMail(EmailVerification emailVerification);
	String verifyToken(String email, int token);

}
