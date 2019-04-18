package com.project.artistPortfolio.ArtistPortfolio.DTO;

import java.util.List;

import com.project.artistPortfolio.ArtistPortfolio.model.Media;

/**
 * This DTO is used for mapping media model with profile name of artist.
 * @author anjuk
 *
 */
public class MediaArtistDTO {
	
	private String profileName;
	private List<Media> mediaList;
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public List<Media> getMediaList() {
		return mediaList;
	}
	public void setMediaList(List<Media> mediaList) {
		this.mediaList = mediaList;
	}
	
	

}
