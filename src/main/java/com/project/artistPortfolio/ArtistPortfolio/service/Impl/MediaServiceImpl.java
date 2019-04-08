package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.repository.MediaRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.MediaService;

@Service
public class MediaServiceImpl implements MediaService{
	
	@Autowired
	private MediaRepository mediaRepository;
	
	private final static Logger logger = LoggerFactory.getLogger(MediaServiceImpl.class);
	
	public Media getMediaById(int id) {
		
		try {
			Media media = mediaRepository.findById(id).get();
			return media;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.BAD_REQUEST);
		}
	}
	
	public Media createMedia(Media media) {
		
		mediaRepository.save(media);
		logger.info("data inserted in media table and now prinintg the media id");
		System.out.println(media.getId());
		return media;
	}
	
	public void updateMedia(int id,Media media) {
		
		Media existingMedia = getMediaById(id);
		if ( existingMedia!=null ) {
			existingMedia.setFileName(media.getFileName());
			existingMedia.setFilenameOriginal(media.getFilenameOriginal());
			existingMedia.setPath(media.getPath());
			existingMedia.setPathThumb(media.getPathThumb());
			mediaRepository.save(existingMedia);
		}	
	}
	
	public String deleteMediaById(int id) {
		
		mediaRepository.deleteById(id);
		return "media deleted";
	}

}
