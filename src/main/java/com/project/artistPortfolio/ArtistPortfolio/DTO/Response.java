package com.project.artistPortfolio.ArtistPortfolio.DTO;

public class Response<ResponseData> {
	
	private ResponseData response;
	
	enum HttpStatusMsg{
		ok, badRequest;
	}
	
	String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ResponseData getResponse() {
		return response;
	}

	public void setResponse(ResponseData response) {
		this.response = response;
	}

	
}
