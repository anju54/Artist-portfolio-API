package com.project.artistPortfolio.ArtistPortfolio.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "exhibition_media")
public class ExhibitionMedia {
	
	@EmbeddedId
	private ExhibitionMediaKey exhibitionMediaKey;
	
	@JsonIgnore
	@ManyToOne
	@MapsId("media_id")
	@JoinColumn(name = "media_id")
	private Media media;
	
	@JsonIgnore
	@ManyToOne
	@MapsId("exhibition_id")
	@JoinColumn(name = "exhibition_id")
	private Exhibition exhibition;
	
	@Column(name="status")
	private String status;
	
	@Column(name="reason")
	private String reason;

	public ExhibitionMediaKey getExhibitionMediaKey() {
		return exhibitionMediaKey;
	}

	public void setExhibitionMediaKey(ExhibitionMediaKey exhibitionMediaKey) {
		this.exhibitionMediaKey = exhibitionMediaKey;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public Exhibition getExhibition() {
		return exhibition;
	}

	public void setExhibition(Exhibition exhibition) {
		this.exhibition = exhibition;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	

}
