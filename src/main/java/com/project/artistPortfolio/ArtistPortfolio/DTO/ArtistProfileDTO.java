package com.project.artistPortfolio.ArtistPortfolio.DTO;

import java.util.List;

import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMedia;
import com.project.artistPortfolio.ArtistPortfolio.model.PaintingType;

public class ArtistProfileDTO {
	
	private String profileName;
	private String facebookUrl;
	private String twitterUrl;
	private String linkedinUrl;
	private String aboutMe;
	private int userId;

	private List<ArtistProfileMedia> artistProfileMedia;
	
	private List<PaintingType> paintingType;
	
	private int profilePicId;
	
	public String getProfileName() {
		return profileName;
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<ArtistProfileMedia> getArtistProfileMedia() {
		return artistProfileMedia;
	}
	public void setArtistProfileMedia(List<ArtistProfileMedia> artistProfileMedia) {
		this.artistProfileMedia = artistProfileMedia;
	}
	public List<PaintingType> getPaintingType() {
		return paintingType;
	}
	public void setPaintingType(List<PaintingType> paintingType) {
		this.paintingType = paintingType;
	}
	public int getProfilePicId() {
		return profilePicId;
	}
	public void setProfilePicId(int profilePicId) {
		this.profilePicId = profilePicId;
	}
}
