package com.project.artistPortfolio.ArtistPortfolio.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.project.artistPortfolio.ArtistPortfolio.model.Media;

public interface MediaService {
	
	Media getMediaById(int id);
	Media createMedia(Media media);
	void updateMedia(int id,Media media);
	String deleteMediaById(int id);
	
	void updateProfilePic(String email,MultipartFile file) throws IOException;

}
