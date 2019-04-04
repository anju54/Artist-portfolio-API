package com.project.artistPortfolio.ArtistPortfolio.DTO;

import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMediaKey;

public class ArtistProfileMediaDTO {
	
	private int artistProfileId;
	private int mediaId;
	private boolean isPublic;
	
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
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	
}
