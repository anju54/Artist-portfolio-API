package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.MediaRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.MediaService;
import com.project.artistPortfolio.ArtistPortfolio.service.MediaStorageService;
import com.project.artistPortfolio.ArtistPortfolio.service.UserService;

@Service
public class MediaServiceImpl implements MediaService{
	
	@Autowired
	private MediaRepository mediaRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private  MediaStorageService fileStorageService;
	
	private final static Logger logger = LoggerFactory.getLogger(MediaServiceImpl.class);
	
	@Override
	public Media getMediaById(int id) {
		
		try {
			Media media = mediaRepository.findById(id).get();
			return media;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public Media createMedia(Media media) {
		
		mediaRepository.save(media);
		logger.info("data inserted in media table and now prinintg the media id");
		System.out.println(media.getId());
		return media;
	}
	
	/**
	 * This is used to update profile pic of particular user
	 * @param email
	 * @param file
	 * @throws IOException
	 */
	@Override
	public void updateProfilePic(String email,MultipartFile file) throws IOException {
		
		UserModel existingUser = userService.getUserByEmail(email);
		Media existingMedia = existingUser.getArtistProfile().getMedia();
		
		String filename = file.getOriginalFilename();
		String uploadLocation = ".././media/artist-profile-pics/";
		
		fileStorageService.uploadFile(file,uploadLocation);
		
		existingMedia.setPath("/media/artist-profile-pics/");
		existingMedia.setFileName("profile-pic-"+filename);
		existingMedia.setFilenameOriginal(filename);
		
		mediaRepository.save(existingMedia);	
	}
	
	@Override
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
	
	@Override
	public String deleteMediaById(int id) {
		
		mediaRepository.deleteById(id);
		return "media deleted";
	}

}
