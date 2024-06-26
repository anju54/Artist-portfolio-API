package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.DTO.ArtistProfileMediaDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMedia;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMediaKey;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.repository.ArtistProfileMediaRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileMediaService;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileService;
import com.project.artistPortfolio.ArtistPortfolio.service.MediaService;

/**
 * This is used to handle all the operation related to ArtistProfileMedia
 * @author anjuk
 *
 */
@Service
public class ArtistProfileMediaServiceImpl implements ArtistProfileMediaService{
	
	@Autowired
	private MediaService mediaService;

	@Autowired
	private ArtistProfileService artistProfileService;
	
	@Autowired
	private  ArtistProfileMediaRepository artistProfileMediaRepository;
	
	private final static Logger logger = LoggerFactory.getLogger(ArtistProfileMediaServiceImpl.class);

	/**
	 * This is used for creating link between artistProfile and media
	 * @param ArtistProfileDTO
	 */
	public void createArtistProfileMediaLink(ArtistProfileMediaDTO artistProfileMediaDTO) {
		
		ArtistProfileMedia artistProfileMedia = new ArtistProfileMedia();
		
		Media media = mediaService.getMediaById(artistProfileMediaDTO.getMediaId());
		
		ArtistProfile artistProfile = artistProfileService.getArtistProfileById(artistProfileMediaDTO.getArtistProfileId());
		
		artistProfileMedia.setArtistProfile(artistProfile);
		artistProfileMedia.setMedia(media);
		artistProfileMedia.setPublicImage(artistProfileMediaDTO.getPublicImage());
		
		artistProfileMedia.setArtistProfileMediaKey(new ArtistProfileMediaKey(artistProfileMediaDTO.getArtistProfileId(),
				artistProfileMediaDTO.getMediaId()));
		
		artistProfileMediaRepository.save(artistProfileMedia);
		
		logger.info("data inserted in bridge table");
	}

}
