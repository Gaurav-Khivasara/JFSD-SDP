package com.klef.jfsd.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.sdp.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	
	// TODO
//	User findByUsername(String username);
	@Query("SELECT u FROM User u WHERE userId=?1")
	User findUserByUserId(long userId);

}
