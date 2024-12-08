package com.klef.jfsd.sdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.jfsd.sdp.dto.EmailTokenPasswordDto;
import com.klef.jfsd.sdp.dto.ForgotPasswordDTO;
import com.klef.jfsd.sdp.dto.TokenSuccessDTO;
import com.klef.jfsd.sdp.model.ForgotPassword;
import com.klef.jfsd.sdp.service.ForgotPasswordServiceImpl;

import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/api/emails/forgot-password")
public class ForgotPasswordController {
	
	@Autowired
	private ForgotPasswordServiceImpl forgotPasswordService;
	
	@PostMapping("/send")
	public ResponseEntity<ForgotPasswordDTO> sendMail(@RequestBody ForgotPassword forgotPassword) {
		String msg = forgotPasswordService.sendMail(forgotPassword);
		
		HttpStatus status = msg.equals("Email sent Successfully!") ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;

		return new ResponseEntity<>(new ForgotPasswordDTO(forgotPassword, msg), status);
	}
	
	@PostMapping("/verify")
	public ResponseEntity<TokenSuccessDTO> verifyToken(@RequestBody EmailTokenPasswordDto emailTokenPasswordDto) {
		String email = emailTokenPasswordDto.getEmail();
		
		String msg = forgotPasswordService.verifyToken(email, emailTokenPasswordDto.getToken(), emailTokenPasswordDto.getPassword());
		
		HttpStatus status = (msg.equals("Invalid Email!") || msg.equals("Expired token!")) ? HttpStatus.UNAUTHORIZED :
			(msg.equals("Link already used!") ? HttpStatus.CONFLICT :
				(msg.equals("Expired token!") ? HttpStatus.FORBIDDEN : HttpStatus.OK));
		
		return new ResponseEntity<>(new TokenSuccessDTO(email, msg), status);
	}
	
}
