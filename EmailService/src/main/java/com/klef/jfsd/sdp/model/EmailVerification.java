package com.klef.jfsd.sdp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class EmailVerification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	@Min(100000)
	@Max(999999)
	private int token;
	
	@Column(nullable = false)
	private String userEmail;
	
	@Column(nullable = false)
	private boolean isVerified;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdOn;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime expiresOn;
	
	@PrePersist
	private void setDateTime() {
		if (this.createdOn == null) {
			this.createdOn = LocalDateTime.now();
		}
		
		if (this.expiresOn == null) {
			this.expiresOn = this.createdOn.plusHours(1);
		}
	}
	
	public EmailVerification() { }

	public EmailVerification(int id, int token, String userEmail, boolean isVerified, LocalDateTime createdOn, LocalDateTime expiresOn) {
		this.id = id;
		this.token = token;
		this.userEmail = userEmail;
		this.isVerified = isVerified;
		this.createdOn = createdOn;
		this.expiresOn = expiresOn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(LocalDateTime expiresOn) {
		this.expiresOn = expiresOn;
	}

	@Override
	public String toString() {
		return "id = " + id + "\ntoken = " + token + "\nuserEmail = " + userEmail + "\nisVerified = "
				+ isVerified + "\ncreatedOn = " + createdOn + "\nexpiresOn = " + expiresOn;
	}

}
