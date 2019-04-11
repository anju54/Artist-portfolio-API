package com.project.artistPortfolio.ArtistPortfolio.DTO;

import com.project.artistPortfolio.ArtistPortfolio.model.Media;

public class ArtistProfilePic {
	
	private Media media;
	private String profileName;
	private String fullName;
	private int artistProfileId;
	
	
	public int getArtistProfileId() {
		return artistProfileId;
	}
	public void setArtistProfileId(int artistProfileId) {
		this.artistProfileId = artistProfileId;
	}
	public Media getMedia() {
		return media;
	}
	public void setMedia(Media media) {
		this.media = media;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
