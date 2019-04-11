package com.project.artistPortfolio.ArtistPortfolio.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "artist_profile_media")
public class ArtistProfileMedia {
	
	@EmbeddedId
	private ArtistProfileMediaKey artistProfileMediaKey;
	
	@JsonIgnore
	@ManyToOne
    @MapsId("artist_profile_id")
    @JoinColumn(name = "artist_profile_id")
    private ArtistProfile artistProfile;
	
	@JsonIgnore
	@OneToOne
    @MapsId("media_id")
    @JoinColumn(name = "media_id")
    private Media media;
	
	@Column(name="is_public")
	@Size(max=5)
	private String publicImage;
	
	public ArtistProfileMedia() {
		super();
	}
	
	public String getPublicImage() {
		return publicImage;
	}

	public void setPublicImage(String publicImage) {
		this.publicImage = publicImage;
	}

	public ArtistProfileMedia(ArtistProfileMediaKey artistProfileMediaKey, ArtistProfile artistProfile, Media media,
			String publicImage) {
		super();
		this.artistProfileMediaKey = artistProfileMediaKey;
		this.artistProfile = artistProfile;
		this.media = media;
		this.publicImage = publicImage;
	}

	public ArtistProfileMediaKey getArtistProfileMediaKey() {
		return artistProfileMediaKey;
	}

	public void setArtistProfileMediaKey(ArtistProfileMediaKey artistProfileMediaKey) {
		this.artistProfileMediaKey = artistProfileMediaKey;
	}

	public ArtistProfile getArtistProfile() {
		return artistProfile;
	}

	public void setArtistProfile(ArtistProfile artistProfile) {
		this.artistProfile = artistProfile;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	

}
