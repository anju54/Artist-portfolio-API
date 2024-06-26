package com.project.artistPortfolio.ArtistPortfolio.DTO;

/**
 * RegistrationDTO is POJO class used for mapping registration requests of user.
 * @author anju.kumari
 *
 */
public class RegistrationDTO {
	
	 private String fname;
	 private String lname;
	 private String email;
	 private String roleName;
	 
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	 
	 

}
