package com.project.artistPortfolio.ArtistPortfolio.DTO;

import javax.persistence.Column;
import javax.validation.constraints.Size;

public class MediaDTO {
	
private int id;
	
	private String fileName;
	private String filenameOriginal;
	private String path;
	private String pathThumb;
	private boolean isPublic;
	
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
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
	

}
