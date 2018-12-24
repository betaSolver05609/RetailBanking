package com.tcs.ilp.LedFloyd.Login.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TBL_LOGIN_LEDFLOYD")
public class Login {
	
	@Id
	@Column(name = "username")
	String username;
	
	@Column(name = "password")
	String password;
	
	@Column(name = "role")
	String role;
	
	@Column(name = "active")
	boolean active;

	@Column(name = "LastTouched")
	@Temporal(TemporalType.TIMESTAMP)
	Date lastUsed;
	
	

	public Login() {
		super();
	}
	

	public Login(String username, String password, String role, boolean active, Date lastUsed) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
		this.active = active;
		this.lastUsed = lastUsed;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}

	
}
