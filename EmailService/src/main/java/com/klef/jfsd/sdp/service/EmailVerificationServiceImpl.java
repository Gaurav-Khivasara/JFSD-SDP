package com.klef.jfsd.sdp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdp.model.EmailVerification;
import com.klef.jfsd.sdp.repository.EmailVerificationRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {
	
	@Autowired
	private EmailVerificationRepository emailVerificationRepo;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public String sendMail(EmailVerification emailVerification) {
		// resend handled!
		List<EmailVerification> emailVerificationList = emailVerificationRepo.getByEmail(emailVerification.getUserEmail());
		if (emailVerificationList.size() != 0) {
			EmailVerification ev = emailVerificationList.get(emailVerificationList.size() - 1);
			if (ev.isVerified()) {
				return "Email already verified!"; 
			}
			
			emailVerification.setCreatedOn(ev.getCreatedOn());
			emailVerification.setExpiresOn(ev.getExpiresOn());
			emailVerification.setToken(ev.getToken());
			
			if (LocalDateTime.now().isAfter(ev.getExpiresOn())) {
				emailVerification.setToken(100000 + new Random().nextInt(900000));
			}
		} else {
			emailVerification.setToken(100000 + new Random().nextInt(900000));
			emailVerification.setVerified(false);
		}
		
		String msg = """
				<html>
				<head>
				<style>
					* {
				    	font-family: monospace;
				    	color: black;
				    }
				    p {
				    	font-size: 17px;
				    }
				    h2 {
				    	font-size: 20px;
				        background: lightgrey;
				        width: fit-content;
				        padding: 5px;
				        letter-spacing: 1.3px;
				    }
				</style>
				</head>
				<body>
				<p>Your verification token is:</p>
				<h2>%d</h2>
				<p>This token will expire in <u>1 hr</u>.</p>
				</body>
				</html>
				""".formatted(emailVerification.getToken());
		
		MimeMessage mimeMsg = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMsgHelper = new MimeMessageHelper(mimeMsg);
		try {
			mimeMsgHelper.setFrom("gauravkhivasara0@gmail.com");
			mimeMsgHelper.setSubject("Email Verification");
			mimeMsgHelper.setTo(emailVerification.getUserEmail());
			mimeMsgHelper.setText(msg, true);
//			javaMailSender.send(mimeMsg);
			
			emailVerificationRepo.save(emailVerification);
			return "Email sent Successfully!";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "MessagingException";
		}
	}

	@Override
	public String verifyToken(String email, int token) {
		// agar ek se jyada h to already pehle wale expire ho gaye honge
		// get the last from the list and check for it directly
		List<EmailVerification> emailVerificationList = emailVerificationRepo.getByEmail(email);
		if (emailVerificationList.size() == 0) {
			return "Invalid Email!";
		}
		
		EmailVerification emailVerification = emailVerificationList.get(emailVerificationList.size() - 1);
		if (emailVerification.isVerified()) {
			return "Email already verified!";
		}
		
		if (emailVerification.getToken() == token) {
			if (LocalDateTime.now().isAfter(emailVerification.getExpiresOn())) {
				return "Expired token!";
			}
			
			emailVerification.setVerified(true);
			emailVerificationRepo.save(emailVerification);
			return "Email verified Successfully!";
		}
		
		return "Invalid Token!";
	}

}
