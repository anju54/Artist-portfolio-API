package com.project.artistPortfolio.ArtistPortfolio.DTO;

/**
 * This is POJO class used to model user.
 * @author anjuk
 *
 */
public class UpdateUserDTO {
	
	private String fname;
	private String lname;
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
}
