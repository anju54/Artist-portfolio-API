package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.DTO.ArtistProfileDTO;
import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMedia;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.PaintingType;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.ArtistProfileRepository;
import com.project.artistPortfolio.ArtistPortfolio.repository.PaintingTypeRepository;
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
	
//	@Autowired
//	private PaintingTypeService paintingTypeService;
		
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
	 * This is used to display list of media of particular artist.
	 * @param id
	 * @return
	 */
	public List<ArtistProfileMedia> getListOfMedia(int id){
		
		ArtistProfile existingRecord = artistProfileRepository.findById(id).get();
		return existingRecord.getArtistProfileMedia();
	}
	
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
		List<PaintingType> paintingTypesSet = new ArrayList<PaintingType>();
		List<PaintingType> paintingTypeLists = artistProfileDTO.getPaintingType();
		for (PaintingType paintingTypeList: paintingTypeLists) {
			
			PaintingType p = paintingTypeRepo.findPaintingTypeByPaintingName(paintingTypeList.getPaintingName());
			paintingTypesSet.add(p);	
		}
		artistProfile.setPaintingType(paintingTypesSet); // list of painting type.
		//------------------------------------------------------------
		
		UserModel user = userService.getUserById(artistProfileDTO.getUserId());
		artistProfile.setUser(user);
		
		Media media = mediaService.getMediaById(artistProfileDTO.getProfilePicId()); //for profile picture.
		artistProfile.setMedia(media);
		
		artistProfileRepository.save(artistProfile);	
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
			logger.info("trying to fecch artist profile detail");
			ArtistProfile artistProfile = artistProfileRepository.findById(id).get();
			logger.info(artistProfile.getPaintingType().get(0).getPaintingName());
			return artistProfile;
		}catch (Exception e) {
				logger.error(e.getMessage());
				throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
	}
	
	public String deleteByid(int id) {
		
		artistProfileRepository.deleteById(id);
		return "record has been deleted";
	}

}
