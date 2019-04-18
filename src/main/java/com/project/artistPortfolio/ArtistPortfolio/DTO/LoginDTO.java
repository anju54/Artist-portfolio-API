package com.project.artistPortfolio.ArtistPortfolio.DTO;

import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is POJO class used to model Login requests.
 * @author anjuk
 *
 */
public class LoginDTO 
{
	/*
	 * Attributes of loginDTO
	 */
	 @NotEmpty( message = "email cant be null")
	 private String email;
	 
	 @NotEmpty( message = "password cant be null")
	 private String password;
	
	 private final static Logger logger = LoggerFactory.getLogger(LoginDTO.class);
	  
	 LoginDTO() {
		 logger.info(" logindto calling");
	 }

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	 
	 
}
