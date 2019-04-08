package com.project.artistPortfolio.ArtistPortfolio.service;

import com.project.artistPortfolio.ArtistPortfolio.model.Media;

public interface MediaService {
	
	Media getMediaById(int id);
	Media createMedia(Media media);
	void updateMedia(int id,Media media);
	String deleteMediaById(int id);

}
