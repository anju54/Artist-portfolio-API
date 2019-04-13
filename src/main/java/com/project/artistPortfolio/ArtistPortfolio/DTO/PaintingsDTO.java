package com.project.artistPortfolio.ArtistPortfolio.DTO;

import com.project.artistPortfolio.ArtistPortfolio.model.Media;

/**
 * DTO for displaying all the public and private paintings of artist
 * @author anju.kumari
 *
 */
public class PaintingsDTO {
	
	private Media media;
	private String publicImage;
	
	public Media getMedia() {
		return media;
	}
	public void setMedia(Media media) {
		this.media = media;
	}
	public String getPublicImage() {
		return publicImage;
	}
	public void setPublicImage(String publicImage) {
		this.publicImage = publicImage;
	}
}
