package com.project.artistPortfolio.ArtistPortfolio.DTO;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ErrorResponseDTO {
	
	private String message;
	private List<String> details;
	
	public ErrorResponseDTO(String message, List<String> details) {
		super();
		this.message = message;
		this.details = details;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getDetails() {
		return details;
	}
	public void setDetails(List<String> details) {
		this.details = details;
	}
	
	
	
	

}
