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

}
