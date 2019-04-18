package com.project.artistPortfolio.ArtistPortfolio.DTO;

import java.util.List;

/**
 * This DTO is used for mapping Artist Profile model.
 * @author anjuk
 *
 */
public class ArtistProfileDTO {
	
	private String fName;
	private String lName;
	private String profileName;
	private String facebookUrl;
	private String twitterUrl;
	private String linkedinUrl;
	private String aboutMe;
	private String email;
	private List<String> paintingType;
	private String colorName;
	
	// setter and getters 

	public String getProfileName() {
		return profileName;
	}
	
	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getFacebookUrl() {
		return facebookUrl;
	}
	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}
	public String getTwitterUrl() {
		return twitterUrl;
	}
	public void setTwitterUrl(String twitterUrl) {
		this.twitterUrl = twitterUrl;
	}
	public String getLinkedinUrl() {
		return linkedinUrl;
	}
	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getPaintingType() {
		return paintingType;
	}
	public void setPaintingType(List<String> paintingType) {
		this.paintingType = paintingType;
	}
	
	
}
