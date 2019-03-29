package com.project.artistPortfolio.ArtistPortfolio.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "artist_profile_media")
public class ArtistProfileMedia {
	
	@EmbeddedId
	private ArtistProfileMediaKey id;
	
	@ManyToOne
    @MapsId("artist_profile_id")
    @JoinColumn(name = "artist_profile_id")
    private ArtistProfile artistProfile;
	
	@ManyToOne
    @MapsId("media_id")
    @JoinColumn(name = "media_id")
    private Media media;
	
	@Column(name="is_public")
	private boolean isPublic;
	
	public ArtistProfileMedia(ArtistProfile artistProfile, Media media) {
		super();
		this.artistProfile = artistProfile;
		this.media = media;
	}

	public ArtistProfileMedia() {
		super();
	}

	public ArtistProfileMediaKey getId() {
		return id;
	}

	public void setId(ArtistProfileMediaKey id) {
		this.id = id;
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
