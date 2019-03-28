package com.project.artistPortfolio.ArtistPortfolio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "media")
public class Media {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="media_id")
	private int id;
	
	@NotNull
	@Column(name = "file_name")
	@Size(max=40)
	private String fileName;
	 
	@NotNull
	@Column(name = "filename_original")
	@Size(max=40)
	private String filenameOriginal;
	 
	@NotNull
	@Column(name = "path")
	@Size(max=40)
	private String path;
	 
	@NotNull
	@Column(name = "path_thumb")
	@Size(max=40)
	private String pathThumb;
	
	@OneToMany(mappedBy = "media") 
	private List<ArtistProfileMedia> artistProfileMedia;

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
