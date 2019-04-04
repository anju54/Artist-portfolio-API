package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.DTO.ArtistProfileDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.ArtistProfileMediaDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.MediaDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.ProfileDTO;
import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMedia;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMediaKey;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.PaintingType;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.ArtistProfileRepository;
import com.project.artistPortfolio.ArtistPortfolio.repository.PaintingTypeRepository;
import com.project.artistPortfolio.ArtistPortfolio.repository.UserRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileMediaService;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileService;
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
	private UserRepository userRepository;
	
	@Autowired
	private ArtistProfileMediaService artistProfileMediaService;
	
//	@Autowired
//	private PaintingTypeService paintingTypeService;
	
	/**
	 * This is used to get artist profile pic path by profile id
	 * @param id
	 * @return Media object
	 */
	public Media getProfilePicByArtistProfileId(int id) {
		
		ArtistProfile artistProfile = getArtistProfileById(id);
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
	public ProfileDTO getArtistPublicProfileInfo(String email, String profileName) {
		
		ProfileDTO profileDTO = new ProfileDTO();
		
		ArtistProfile artistProfile = getArtistProfileByProfileName(profileName);
		
		profileDTO.setAboutMe(artistProfile.getAboutMe());
		profileDTO.setFacebookUrl(artistProfile.getFacebookUrl());
		profileDTO.setLinkedinUrl(artistProfile.getLinkedinUrl());
		profileDTO.setTwitterUrl(artistProfile.getTwitterUrl());
		profileDTO.setProfileName(profileName);
		profileDTO.setPaintingType(artistProfile.getPaintingType());
		
		UserModel user = userRepository.findByEmail(email);
		profileDTO.setFname(user.getFname());
		profileDTO.setLname(user.getLname());
		profileDTO.setEmail(user.getEmail());
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
		List<PaintingType> paintingTypeLists = artistProfileDTO.getPaintingType(); // input list of painting type
		for (PaintingType paintingTypeList: paintingTypeLists) {
			
			PaintingType p = paintingTypeRepo.findPaintingTypeByPaintingName(paintingTypeList.getPaintingName());
			paintingTypesSet.add(p);	
		}
		artistProfile.setPaintingType(paintingTypesSet); // list of painting type.
		//------------------------------------------------------------
		
		UserModel user = userService.getUserByEmail(artistProfileDTO.getEmail());
		artistProfile.setUser(user);
		
//		Media media = mediaService.getMediaById(artistProfileDTO.getProfilePicId()); //for profile picture.
//		artistProfile.setMedia(media);
		
		artistProfileRepository.save(artistProfile);	
	}
	
	/**
	 * this is used to create media in list and link to artist profile
	 * @param List of MediaDTO
	 * @param profileName
	 * 			profile name of artist
	 */
	@Override
	public void addArtistProfileMedia(List<MediaDTO> mediaList,String profileName) {
		
		for(MediaDTO mediaDTO: mediaList) {
			
			Media media = new Media();
			media.setFileName(mediaDTO.getFileName());
			media.setFilenameOriginal(mediaDTO.getFilenameOriginal());
			media.setPath(mediaDTO.getPath());
			media.setPathThumb(mediaDTO.getPathThumb());
			
			int mediaId = mediaService.createMedia(media);
			
			ArtistProfile exsitingArtistProfile = artistProfileRepository.findByProfileName(profileName);
			int artistProfileId = exsitingArtistProfile.getId();
			
			ArtistProfileMediaDTO artistProfileMediaDTO = new ArtistProfileMediaDTO();
			
			artistProfileMediaDTO.setArtistProfileId(artistProfileId);
			artistProfileMediaDTO.setMediaId(mediaId);
			artistProfileMediaDTO.setPublic(mediaDTO.isPublic());
			
			artistProfileMediaDTO.setArtistProfileMediaKey( new ArtistProfileMediaKey(artistProfileId,mediaId));
			
			logger.info("------------------"+mediaDTO.isPublic());
			
			artistProfileMediaService.createArtistProfileMediaLink(artistProfileMediaDTO);
		}
		
	}
	
	@Override
	public void updateArtistProfileRecord(ArtistProfile artistProfile, int id) {
		
		ArtistProfile existingRecord = getArtistProfileById(id);
		
		if(existingRecord!=null) {
			
			existingRecord.setAboutMe(artistProfile.getAboutMe());
			existingRecord.setFacebookUrl(artistProfile.getFacebookUrl());
			existingRecord.setLinkedinUrl(artistProfile.getLinkedinUrl());
			existingRecord.setTwitterUrl(artistProfile.getTwitterUrl());
			existingRecord.setProfileName(artistProfile.getProfileName());
			
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
