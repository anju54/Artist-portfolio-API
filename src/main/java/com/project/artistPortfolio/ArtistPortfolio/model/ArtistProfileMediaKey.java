package com.project.artistPortfolio.ArtistPortfolio.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ArtistProfileMediaKey implements Serializable{
	
	private static final long serialVersionUID = 8958607353870411854L;

	//@Column(name = "artist_profile_id")
    private int artistProfileId;
 
    //@Column(name = "media_id")
    private int mediaId;

	public ArtistProfileMediaKey() {
		super();
	}

	public ArtistProfileMediaKey(int artistProfileId, int mediaId) {
		super();
		this.artistProfileId = artistProfileId;
		this.mediaId = mediaId;
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
    
 
}
