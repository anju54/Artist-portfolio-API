package com.project.artistPortfolio.ArtistPortfolio.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ExhibitionMediaKey implements Serializable{
	
	@Column(name = "media_id")
	private int mediaId;
	 
	@Column(name = "exhibition_id")
	private int exhibitionId;

	public ExhibitionMediaKey() {
		super();
	}

	public ExhibitionMediaKey(int mediaId, int exhibitionId) {
		super();
		this.mediaId = mediaId;
		this.exhibitionId = exhibitionId;
	}

	public int getMediaId() {
		return mediaId;
	}

	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}

	public int getExhibitionId() {
		return exhibitionId;
	}

	public void setExhibitionId(int exhibitionId) {
		this.exhibitionId = exhibitionId;
	}
	 
	 

}
