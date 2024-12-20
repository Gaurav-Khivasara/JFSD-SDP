package com.klef.jfsd.sdp.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
//public class User implements UserDetails {
	
//	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private Role role;
	
	@Column(nullable = false, unique = true)
	private long userId;
	
	// TODO
//	@Column(nullable = false, unique = true)
//	private String username;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
//	public void setUsername(String username) {
//		this.username = Long.toString(this.userId);
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.role.name()));
	}

//	@Override
//	public String getUsername() {
//		return Long.toString(this.userId);
//	}

//	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

//	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

//	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

//	@Override
	public boolean isEnabled() {
		return true;
	}

}
