package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.artistPortfolio.ArtistPortfolio.DTO.MediaArtistDTO;
import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMedia;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.ArtistProfileMediaRepository;
import com.project.artistPortfolio.ArtistPortfolio.repository.MediaRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileService;
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
	private ArtistProfileService artistProfileService;
	
	@Autowired
	private ArtistProfileMediaRepository artistProfileMediaRepository;
	
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
	
	/**
	 * This is used to get all paintings of particular artist
	 * 
	 * @return list of media object
	 */
	public List<Media> getMediaByArtistProfileMediaKey(Authentication authentication) {
		
		List<Media> mediaList = new ArrayList<Media>(); 
		
		String email =  userService.getPrincipalUser(authentication).getUsername();
		int artistProfileId = userService.getUserByEmail(email).getArtistProfile().getId();
		
		List<ArtistProfileMedia> artistProfileMediaList = artistProfileMediaRepository.findArtistProfileMediaByArtistProfileId(artistProfileId);
		for(ArtistProfileMedia artistProfileMedia: artistProfileMediaList) {
			
			mediaList.add(artistProfileMedia.getMedia());	
		}
		return mediaList;	
	}
	
	/**
	 * This is used to get all public paintings of particular artist
	 * 
	 * @return list of media object
	 */
	public MediaArtistDTO getPublicMedia(Authentication authentication){
		
		List<Media> mediaList = new ArrayList<Media>(); 
		
		MediaArtistDTO mediaArtistDTO = new MediaArtistDTO();
		
		String email =  userService.getPrincipalUser(authentication).getUsername();
		int artistProfileId = userService.getUserByEmail(email).getArtistProfile().getId();
		
		List<ArtistProfileMedia> artistProfileMediaList = artistProfileMediaRepository.findArtistProfileMediaByArtistProfileId(artistProfileId);
		for(ArtistProfileMedia artistProfileMedia: artistProfileMediaList) {
			
			if(artistProfileMedia.getPublicImage().equalsIgnoreCase("true")) {
				
				mediaList.add(artistProfileMedia.getMedia());
			}
		}
		
		mediaArtistDTO.setMediaList(mediaList);
		mediaArtistDTO.setProfileName(artistProfileService.getArtistProfileById(artistProfileId).getProfileName());
		return mediaArtistDTO;
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
	public void updateProfilePic(Authentication authentication,MultipartFile file) throws IOException {
		
		UserModel existingUser = userService.getUserByEmail((userService.getPrincipalUser(authentication).getUsername()));
		Media existingMedia = existingUser.getArtistProfile().getMedia();
		
		String filename = file.getOriginalFilename();
		String uploadLocation = "../ArtistPortfolioAPI/media/artist-profile-pics/";
		String fileType = "profile-pic";
		fileStorageService.uploadFile(file,uploadLocation,fileType,authentication);
		
		existingMedia.setPath("/media/artist-profile-pics/");
		existingMedia.setFileName("profile-pic-"+filename);
		existingMedia.setFilenameOriginal(filename);
		
//		ArtistProfile artistProfile = existingMedia.getArtistProfile();
//		
//		artistProfile.setMedia(existingMedia);
//		artistProfileRepository.save(artistProfile);
		
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
