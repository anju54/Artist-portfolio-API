package com.project.artistPortfolio.ArtistPortfolio.DTO;

/**
 * This DTO is holding the information of Principal User.
 * @author anjuk
 *
 */
public class CurrentUserDTO {
	
	private String username;
	private String fullName;
	private String userType;
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
