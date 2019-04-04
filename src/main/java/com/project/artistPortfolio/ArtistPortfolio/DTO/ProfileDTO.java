package com.project.artistPortfolio.ArtistPortfolio.DTO;

import java.util.List;

import com.project.artistPortfolio.ArtistPortfolio.model.PaintingType;

public class ProfileDTO {
	
	private String fname;
	private String lname;
	private String email;
	private String facebookUrl;
	private String twitterUrl;
	private String linkedinUrl;
	private String aboutMe;
	private String profileName;
	private List<PaintingType> paintingType;
	
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
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public List<PaintingType> getPaintingType() {
		return paintingType;
	}
	public void setPaintingType(List<PaintingType> list) {
		this.paintingType = list;
	}
}
