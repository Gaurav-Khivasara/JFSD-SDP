package com.klef.jfsd.sdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.jfsd.sdp.dto.EmailTokenDto;
import com.klef.jfsd.sdp.dto.EmailVerificationDTO;
import com.klef.jfsd.sdp.dto.TokenSuccessDTO;
import com.klef.jfsd.sdp.model.EmailVerification;
import com.klef.jfsd.sdp.service.EmailVerificationServiceImpl;

import jakarta.mail.MessagingException;

@CrossOrigin
@RestController
@RequestMapping("/api/emails/email-verification")
public class EmailVerificationController {
	
	@Autowired
	private EmailVerificationServiceImpl emailVerificationService;
	
	// User resend dabata hai to check agar already verified user h to terminate with msg	// Else koi unexpired token h to resend it	// Else Create a new token and send it
	@PostMapping("/send")
	public ResponseEntity<EmailVerificationDTO> sendMail(@RequestBody EmailVerification emailVerification) throws MessagingException {
	// public ResponseEntity<String> sendMail(@RequestBody EmailVerification emailVerification) throws MessagingException {
		String msg = emailVerificationService.sendMail(emailVerification);
		
		HttpStatus status = msg.equals("Email sent Successfully!") ? HttpStatus.OK :
			(msg.equals("Email already verified!") ? HttpStatus.CONFLICT : HttpStatus.INTERNAL_SERVER_ERROR);
		
		// Sending all details of emailVerification model with msg and status to client
		// Bcuz sometimes email might not be sent through KL WiFi
		// Else just return msg and status
		return new ResponseEntity<>(new EmailVerificationDTO(emailVerification, msg), status);
		// return new ResponseEntity<>(msg, status);
	}
	
	@PostMapping("/verify")
	public ResponseEntity<TokenSuccessDTO> verifyToken(@RequestBody EmailTokenDto emailTokenDto) {
		String email = emailTokenDto.getEmail();
		System.out.println("email: " + email);
		System.out.println("token: " + emailTokenDto.getToken());
//		String msg = emailVerificationService.verifyToken(email, Integer.valueOf(request.getParameter("token")));
		String msg = emailVerificationService.verifyToken(emailTokenDto.getEmail(), emailTokenDto.getToken());
		
		HttpStatus status = (msg.equals("Invalid Email!") || msg.equals("Invalid Token!")) ? HttpStatus.UNAUTHORIZED :
			(msg.equals("Email already verified!") ? HttpStatus.CONFLICT :
				(msg.equals("Expired token!") ? HttpStatus.FORBIDDEN : HttpStatus.OK));
		
		return new ResponseEntity<>(new TokenSuccessDTO(email, msg), status);
	}

}
