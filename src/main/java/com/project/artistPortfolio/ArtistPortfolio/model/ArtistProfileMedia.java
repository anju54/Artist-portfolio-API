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
	@ManyToOne
    @MapsId("media_id")
    @JoinColumn(name = "media_id")
    private Media media;
	
	@Column(name="is_public")
	private boolean isPublic;
	
	public ArtistProfileMedia(ArtistProfileMediaKey artistProfileMediaKey, ArtistProfile artistProfile, Media media,
			boolean isPublic) {
		super();
		this.artistProfileMediaKey = artistProfileMediaKey;
		this.artistProfile = artistProfile;
		this.media = media;
		this.isPublic = isPublic;
	}

	public ArtistProfileMedia() {
		super();
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

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

}
