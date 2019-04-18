package com.project.artistPortfolio.ArtistPortfolio.DTO;

/**
 * This DTO is used for mapping the Media model class.
 * @author anjuk
 *
 */
public class MediaDTO {
	
	private String fileName;
	private String path;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
