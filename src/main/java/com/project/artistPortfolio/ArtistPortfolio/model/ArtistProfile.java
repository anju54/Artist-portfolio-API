package com.project.artistPortfolio.ArtistPortfolio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "artist_profile")
public class ArtistProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="artist_profile_id")
	private int id;
	 
	@NotNull
	@Column(name = "profile_name")
	@Size(max=20)
	private String profileName;

	@NotNull
	@Column(name = "facebook_url")
	@Size(max=70)
	private String facebookUrl;
	 
	@NotNull
	@Column(name = "twitter_url")
	@Size(max=70)
	private String twitterUrl;
	 
	@NotNull
	@Column(name = "linkedin_url")
	@Size(max=70)
	private String linkedinUrl;
	 
	@NotNull
	@Column(name = "about_me")
	private String aboutMe;

	@OneToMany(mappedBy = "artistProfile") 
	private List<ArtistProfileMedia> artistProfileMedia;
	
	@ManyToMany
	List<PaintingType> paintingType;
	 
	@OneToOne
	@JoinColumn(name="profile_pic_id")
	private Media media;
	 
	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public List<PaintingType> getPaintingType() {
		return paintingType;
	}

	public void setPaintingType(List<PaintingType> paintingType) {
		this.paintingType = paintingType;
	}

	public List<ArtistProfileMedia> getArtistProfileMedia() {
		return artistProfileMedia;
	}

	public void setArtistProfileMedia(List<ArtistProfileMedia> artistProfileMedia) {
		this.artistProfileMedia = artistProfileMedia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getFacebookUrl() {
		return facebookUrl;
	}

	public void setFacebookUrl(String facebookUrl) {
		this.facebookUrl = facebookUrl;
	}

	public String getTwitterUrl() {
		return twitterUrl;
	}

	public void setTwitterUrl(String twitterUrl) {
		this.twitterUrl = twitterUrl;
	}

	public String getLinkedinUrl() {
		return linkedinUrl;
	}

	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	 
	 
	 
	 
	 
}
