package com.project.artistPortfolio.ArtistPortfolio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "artist_profile")
public class ArtistProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="artist_profile_id")
	private int id;
	 
	@Column(name = "profile_name",unique=true)
	@Size(max=20)
	private String profileName;

	@Column(name = "facebook_url")
	@Size(max=70)
	private String facebookUrl;
	 
	@Column(name = "twitter_url")
	@Size(max=70)
	private String twitterUrl;
	 
	@Column(name = "linkedin_url")
	@Size(max=70)
	private String linkedinUrl;
	
	//@Lob
	@Column(name = "about_me")
	private String aboutMe;
	
	@Column(name = "color_id")
	private int colorId;

	//@JsonIgnore
	@OneToOne
	@JoinColumn(name="user_id")
	private UserModel user;
	
	@OneToOne(mappedBy="artistProfile")
	private Invitation invitation;

	@JsonIgnore
	@OneToMany(mappedBy = "artistProfile") //bridge table
	private List<ArtistProfileMedia> artistProfileMedia;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable( name="artist_painting_bridge", joinColumns=@JoinColumn(name="artist_profile_id"), 
				inverseJoinColumns=@JoinColumn(name = "painting_type_id"))
	List<PaintingType> paintingType;
	
	@JsonIgnore
	@OneToOne()
	@JoinColumn(name="profile_pic_id")
	private Media media;
	
	public int getColorId() {
		return colorId;
	}

	public Invitation getInvitation() {
		return invitation;
	}

	public void setInvitation(Invitation invitation) {
		this.invitation = invitation;
	}

	public void setColorId(int colorId) {
		this.colorId = colorId;
	}
	
	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
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
