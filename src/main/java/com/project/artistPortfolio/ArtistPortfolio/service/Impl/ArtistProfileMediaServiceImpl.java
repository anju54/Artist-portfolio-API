package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMedia;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMediaKey;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.repository.ArtistProfileMediaRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileMediaService;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileService;
import com.project.artistPortfolio.ArtistPortfolio.service.MediaService;

@Service
public class ArtistProfileMediaServiceImpl implements ArtistProfileMediaService{
	
	@Autowired
	private MediaService mediaService;

	@Autowired
	private ArtistProfileService artistProfileService;
	
	@Autowired
	private  ArtistProfileMediaRepository artistProfileMediaRepository;

	public void createArtistProfileMediaLink(ArtistProfileMediaKey artistProfileMediaKey) {
		
		ArtistProfileMedia artistProfileMedia = new ArtistProfileMedia();
		
		Media media = mediaService.getMediaById(artistProfileMediaKey.getMediaId());
		
		ArtistProfile artistProfile = artistProfileService.getArtistProfileById(artistProfileMediaKey.getArtistProfileId());
		
		artistProfileMedia.setArtistProfile(artistProfile);
		artistProfileMedia.setMedia(media);
		
		artistProfileMediaRepository.save(artistProfileMedia);
	}

}
