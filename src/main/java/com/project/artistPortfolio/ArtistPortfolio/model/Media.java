package com.project.artistPortfolio.ArtistPortfolio.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "media")
public class Media {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="media_id")
	private int id;
	
	@Column(name = "file_name",unique=true)
	private String fileName;
	 
	@Column(name = "filename_original",unique=true)
	private String filenameOriginal;
	 
	@Column(name = "path") 
	private String path;
	 
	@Column(name = "path_thumb")
	private String pathThumb;
	
	@JsonIgnore
	@OneToOne(mappedBy = "media", cascade = CascadeType.ALL) 
	private ArtistProfileMedia artistProfileMedia;
	
	@JsonIgnore
	@OneToOne(mappedBy="media", cascade = CascadeType.ALL)
	private ArtistProfile artistProfile;

	public ArtistProfile getArtistProfile() {
		return artistProfile;
	}

	public void setArtistProfile(ArtistProfile artistProfile) {
		this.artistProfile = artistProfile;
	}

	public ArtistProfileMedia getArtistProfileMedia() {
		return artistProfileMedia;
	}

	public void setArtistProfileMedia(ArtistProfileMedia artistProfileMedia) {
		this.artistProfileMedia = artistProfileMedia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilenameOriginal() {
		return filenameOriginal;
	}

	public void setFilenameOriginal(String filenameOriginal) {
		this.filenameOriginal = filenameOriginal;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPathThumb() {
		return pathThumb;
	}

	public void setPathThumb(String pathThumb) {
		this.pathThumb = pathThumb;
	}
	 
	 

}
