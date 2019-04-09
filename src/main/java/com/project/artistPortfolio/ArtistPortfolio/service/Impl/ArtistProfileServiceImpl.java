package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.DTO.*;
import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.*;
import com.project.artistPortfolio.ArtistPortfolio.repository.ArtistProfileRepository;
import com.project.artistPortfolio.ArtistPortfolio.repository.PaintingTypeRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileMediaService;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileService;
import com.project.artistPortfolio.ArtistPortfolio.service.ColorService;
import com.project.artistPortfolio.ArtistPortfolio.service.MediaService;
import com.project.artistPortfolio.ArtistPortfolio.service.UserService;

@Service
public class ArtistProfileServiceImpl implements ArtistProfileService{
	
	private final static Logger logger = LoggerFactory.getLogger(ArtistProfileServiceImpl.class);
	
	@Autowired
	private ArtistProfileRepository artistProfileRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MediaService mediaService;
	
	@Autowired
	private PaintingTypeRepository paintingTypeRepo;
	
	@Autowired
	private ArtistProfileMediaService artistProfileMediaService;
	
	@Autowired
	private ColorService colorService;
	
	/**
	 * This is used to get artist profile pic path by profile id
	 * @param id
	 * 			user id
	 * @return Media object
	 */
	public Media getProfilePicByArtistProfileId(int id) {
		
		ArtistProfile artistProfile = userService.getUserById(id).getArtistProfile();
		File a = new File(artistProfile.getMedia().getPath());
		logger.info(a.getAbsolutePath());
		
		return artistProfile.getMedia();	 
	}
		
	/**
	 * This method will return list of painting type of particular artist e.g. oil painting.
	 * @param id
	 * 			artist profile id.
	 * 
	 * @return List<PaintingType>
	 */
	public List<PaintingType> getListOfPaintingType(int id) {
		
		ArtistProfile existingRecord = artistProfileRepository.findById(id).get();
		return existingRecord.getPaintingType();	
	}
	
	/**
	 * This is used to get artist profile information that will be display over 
	 * artist profile page 
	 * @param email
	 * 		 user's email id.
	 * @param profileName
	 * 		artist profile name.
	 */
	public ProfileDTO getArtistPublicProfileInfo(Authentication authentication) {
		
		String currentUser = userService.getPrincipalUser(authentication).getUsername();
		UserModel user = userService.getUserByEmail(currentUser);
		int id = user.getArtistProfile().getId();
		
		ProfileDTO profileDTO = new ProfileDTO();
		
		ArtistProfile artistProfile = getArtistProfileById(id);
		
		profileDTO.setAboutMe(artistProfile.getAboutMe());
		profileDTO.setFacebookUrl(artistProfile.getFacebookUrl());
		profileDTO.setLinkedinUrl(artistProfile.getLinkedinUrl());
		profileDTO.setTwitterUrl(artistProfile.getTwitterUrl());
		profileDTO.setProfileName(artistProfile.getProfileName());
		profileDTO.setPaintingType(artistProfile.getPaintingType());
		
		profileDTO.setFname(user.getFname());
		profileDTO.setLname(user.getLname());
		profileDTO.setEmail(user.getEmail());
		
		Color color = colorService.getColorById(artistProfile.getColorId());
		profileDTO.setColorName(color.getColorName());
		return profileDTO;
	}
	
	/**
	 * This is used to display list of media of particular artist.
	 * @param id
	 * @return
	 */
	public List<ArtistProfileMedia> getListOfMedia(int id){
		
		ArtistProfile existingRecord = artistProfileRepository.findById(id).get();
		return existingRecord.getArtistProfileMedia();
	}
	
	/**
	 * This is used to create artist profile basic information with painting type
	 * @param ArtistProfileDTO
	 */
	@Override
	public void createArtistProfileRecord(ArtistProfileDTO artistProfileDTO) {
		
		ArtistProfile artistProfile = new ArtistProfile();
		
		artistProfile.setAboutMe(artistProfileDTO.getAboutMe());
		artistProfile.setFacebookUrl(artistProfileDTO.getFacebookUrl());
		artistProfile.setLinkedinUrl(artistProfileDTO.getLinkedinUrl());
		artistProfile.setTwitterUrl(artistProfileDTO.getTwitterUrl());
		artistProfile.setProfileName(artistProfileDTO.getProfileName());
		
		//artistProfile.setArtistProfileMedia(artistProfileDTO.getArtistProfileMedia()); // list of media.
		
		//--------------------------------------------------------
		List<PaintingType> paintingTypesSet = new ArrayList<PaintingType>(); // empty list
		List<String> paintingTypeLists = artistProfileDTO.getPaintingType(); // input list of painting type
		for (String paintingTypeList: paintingTypeLists) {
			
			PaintingType p = paintingTypeRepo.findPaintingTypeByPaintingName(paintingTypeList);
			paintingTypesSet.add(p);	
		}
		artistProfile.setPaintingType(paintingTypesSet); // list of painting type.
		//------------------------------------------------------------
		
		UserModel user = userService.getUserByEmail(artistProfileDTO.getEmail());
		artistProfile.setUser(user);
		
		Color existingColor = colorService.getColorByColorName(artistProfileDTO.getColorName());
		artistProfile.setColorId(existingColor.getId());
		
//		Media media = mediaService.getMediaById(artistProfileDTO.getProfilePicId()); //for profile picture.
//		artistProfile.setMedia(media);
		
		// to create a folder by artist profile name
		Path existingPath = Paths.get("../../Artist_portfolio/ArtistPortfolioAPI/media/"+artistProfileDTO.getProfileName());
		boolean flag = Files.notExists(existingPath);
		if(!flag) {
			logger.info("folder exists");
		}else {
		
			File parentDir = new File("../../Artist_portfolio/ArtistPortfolioAPI/media/");
			File newDir = new File(parentDir, artistProfileDTO.getProfileName());
			newDir.mkdir();
		}
		
		artistProfileRepository.save(artistProfile);	
	}
	
	/**
	 * this is used to create media in list and link to artist profile
	 * @param List of MediaDTO
	 * @param profileName
	 * 			profile name of artist
	 */
	@Override
	public void addArtistProfileMedia(MediaDTO mediaDTO,String profileName) {
		
		//for(MediaDTO mediaDTO: mediaList) {
			
			Media media = new Media();
			media.setFileName(mediaDTO.getFileName());
			
			media.setPath(mediaDTO.getPath());
			
			int mediaId = mediaService.createMedia(media).getId();
			
			ArtistProfile exsitingArtistProfile = artistProfileRepository.findByProfileName(profileName);
			int artistProfileId = exsitingArtistProfile.getId();
			
			ArtistProfileMediaDTO artistProfileMediaDTO = new ArtistProfileMediaDTO();
			
			artistProfileMediaDTO.setArtistProfileId(artistProfileId);
			artistProfileMediaDTO.setMediaId(mediaId);
			
			artistProfileMediaDTO.setArtistProfileMediaKey( new ArtistProfileMediaKey(artistProfileId,mediaId));
			
			artistProfileMediaService.createArtistProfileMediaLink(artistProfileMediaDTO);	
	}
	
	@Override
	public void updateArtistProfileRecord(ArtistProfileDTO artistProfileDTO,String email) {
		
		UserModel user = userService.getUserByEmail(email);
		int artistId = user.getArtistProfile().getId();
		
		ArtistProfile existingRecord = getArtistProfileById(artistId);
		
		if(existingRecord!=null) {
			
			existingRecord.setAboutMe(artistProfileDTO.getAboutMe());
			existingRecord.setFacebookUrl(artistProfileDTO.getFacebookUrl());
			existingRecord.setLinkedinUrl(artistProfileDTO.getLinkedinUrl());
			existingRecord.setTwitterUrl(artistProfileDTO.getTwitterUrl());
			existingRecord.setProfileName(artistProfileDTO.getProfileName());
			
			List<PaintingType> paintingTypesSet = new ArrayList<PaintingType>(); // empty list
			List<String> paintingTypeLists = artistProfileDTO.getPaintingType(); // input list of painting type
			for (String paintingTypeList: paintingTypeLists) {
				
				PaintingType p = paintingTypeRepo.findPaintingTypeByPaintingName(paintingTypeList);
				paintingTypesSet.add(p);	
			}
			existingRecord.setPaintingType(paintingTypesSet); // list of painting type.
			
			Color existingColor = colorService.getColorByColorName(artistProfileDTO.getColorName());
			existingRecord.setColorId(existingColor.getId());
			
			existingRecord.setUser(user);
			
			artistProfileRepository.save(existingRecord);
		}
	}
	
	@Override
	public ArtistProfile getArtistProfileById(int id) {
		
		try {
			logger.info("trying to fecch artist profile detail with id "+id);
			
			ArtistProfile artistProfile = artistProfileRepository.findById(id).get();
			//logger.info(artistProfile.getPaintingType().get(0).getPaintingName());
			return artistProfile;
			
		}catch (Exception e) {
				logger.error(e.getMessage());
				throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
	}
	
	public ArtistProfile getArtistProfileByProfileName(String profileName) {
		
		try {
			return artistProfileRepository.findByProfileName(profileName);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	public String deleteByid(int id) {
		
		artistProfileRepository.deleteById(id);
		return "record has been deleted";
	}

}
