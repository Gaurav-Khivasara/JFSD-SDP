package com.klef.jfsd.sdp.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.klef.jfsd.sdp.model.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
	
	@Query("SELECT t FROM Teacher t WHERE t.email=?1")
	Teacher findByEmail(String email);

}
