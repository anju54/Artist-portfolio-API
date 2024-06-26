package com.project.artistPortfolio.ArtistPortfolio.DTO;

import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMediaKey;

/**
 * This DTO is used for mapping the ArtistProfileMedia model.
 * @author anjuk
 *
 */
public class ArtistProfileMediaDTO {
	
	private int artistProfileId;
	private int mediaId;
	private String publicImage;
	
	private ArtistProfileMediaKey artistProfileMediaKey;
	
	public ArtistProfileMediaKey getArtistProfileMediaKey() {
		return artistProfileMediaKey;
	}
	public void setArtistProfileMediaKey(ArtistProfileMediaKey artistProfileMediaKey) {
		this.artistProfileMediaKey = artistProfileMediaKey;
	}
	public int getArtistProfileId() {
		return artistProfileId;
	}
	public void setArtistProfileId(int artistProfileId) {
		this.artistProfileId = artistProfileId;
	}
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	public String getPublicImage() {
		return publicImage;
	}
	public void setPublicImage(String publicImage) {
		this.publicImage = publicImage;
	}
	
	
}
