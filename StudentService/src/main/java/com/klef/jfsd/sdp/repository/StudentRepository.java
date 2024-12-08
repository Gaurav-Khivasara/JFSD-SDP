package com.klef.jfsd.sdp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.klef.jfsd.sdp.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	
	@Query("SELECT s FROM Student s WHERE s.email=?1")
	Student findByEmail(String email);
	
	@Query("SELECT s FROM Student s WHERE s.id=?1")
	Optional<Student> findById(long id);

}
