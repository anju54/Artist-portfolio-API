package com.project.artistPortfolio.ArtistPortfolio.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface MediaStorageService {
	
	public static final String UPLOAD_LOCATION = "D:\\apache\\apache-tomcat\\webapps\\artist_portfolio\\artist-profile-pic\\";
			//"D:\\Artist_portfolio\\ArtistPortfolioAPI\\media\\profile-pic\\";
			//"D:\\profile-pics\\";
	
	void uploadFile(MultipartFile file) throws IOException;

}
