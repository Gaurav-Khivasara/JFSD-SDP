package com.klef.jfsd.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.sdp.model.EmailVerification;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Integer> {
	
	@Query("SELECT ev FROM EmailVerification ev WHERE ev.userEmail=?1")
	List<EmailVerification> getByEmail(String userEmail);
	
}
