package com.klef.jfsd.sdp.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdp.model.ForgotPassword;
import com.klef.jfsd.sdp.repository.ForgotPasswordRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
	
	@Autowired
	private ForgotPasswordRepository forgotPasswordRepo;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public String hash(String str) {
		String salt = "EmailService";
		StringBuilder hashed = new StringBuilder();
		try {
			for (byte b: MessageDigest.getInstance("SHA-256").digest((str + salt).getBytes())) {
				hashed.append(String.format("%02x", b));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hashed.toString();
	}

	@Override
	public String sendMail(ForgotPassword forgotPassword) {
		// resend handled!
		forgotPassword.setToken(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
		forgotPassword.setVerified(false);
		
		List<ForgotPassword> forgotPasswordList = forgotPasswordRepo.getByEmail(forgotPassword.getUserEmail());
		if (forgotPasswordList.size() != 0) {
			ForgotPassword fp = forgotPasswordList.get(forgotPasswordList.size() - 1);
			if (!fp.isVerified() && LocalDateTime.now().isBefore(fp.getExpiresOn())) {
				forgotPassword.setCreatedOn(fp.getCreatedOn());
				forgotPassword.setExpiresOn(fp.getExpiresOn());
				forgotPassword.setToken(fp.getToken());
			}
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
				<p>Your password reset link is:</p>
				<h2><a href="http://localhost:5173/forgot-password?email=%s&token=%s" >Reset password</a></h2>
				<p>This link will expire in <u>1 hr</u>.</p>
				</body>
				</html>
				""".formatted(forgotPassword.getUserEmail(),
						hash(forgotPassword.getToken()));
		
		MimeMessage mimeMsg = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMsgHelper = new MimeMessageHelper(mimeMsg);
		try {
			mimeMsgHelper.setFrom("gauravkhivasara0@gmail.com");
			mimeMsgHelper.setSubject("Password Reset");
			mimeMsgHelper.setTo(forgotPassword.getUserEmail());
			mimeMsgHelper.setText(msg, true);
//			javaMailSender.send(mimeMsg);
			
			forgotPasswordRepo.save(forgotPassword);
			return "Email sent Successfully!";
		} catch (MessagingException e) {
			e.printStackTrace();
			return "MessagingException";
		}
	}

	@Override
	public String verifyToken(String email, String token, String password) {
		List<ForgotPassword> forgotPasswordList = forgotPasswordRepo.getByEmail(email);
		if (forgotPasswordList.size() == 0) {
			return "Invalid Email!";
		}
		
		ForgotPassword forgotPassword = forgotPasswordList.get(forgotPasswordList.size() - 1);
		if (hash(forgotPassword.getToken()).equals(token)) {
			if (forgotPassword.isVerified()) {
				return "Link already used!";
			}
			
			if (LocalDateTime.now().isAfter(forgotPassword.getExpiresOn())) {
				return "Expired token!";
			}
			
			forgotPassword.setVerified(true);
			forgotPasswordRepo.save(forgotPassword);
			
			jdbcTemplate.update("UPDATE teacher SET password = ? WHERE email = ?", password, email);
			jdbcTemplate.update("UPDATE student SET password = ? WHERE email = ?", password, email);
			
			return "Token verified Successfully!";
		}
		
		return "Invalid token!";
	}
	
}
