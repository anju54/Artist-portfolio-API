package com.project.artistPortfolio.ArtistPortfolio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Model class for users table 
 * @author anjuk
 *
 */
@Entity
@Table(name = "users")
public class UserModel {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="user_id")
	 private int id;
	 
	 @NotNull
	 @Column(name = "fname")
	 @Size(max=20)
	 private String fname;
	 
	 @Column(name = "lname")
	 @Size(max=20)
	 private String lname;
	 
	 @NotNull
	 @Column(name = "email", unique = true)
	 @Size(max=40)
	 private String email;
	 
	 @Column(name = "password")
	 private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

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
	 
//	 @ManyToOne
//	 @JoinColumn(name = "role_id", nullable = false)
//	 private Role role;
	 
	 

}
