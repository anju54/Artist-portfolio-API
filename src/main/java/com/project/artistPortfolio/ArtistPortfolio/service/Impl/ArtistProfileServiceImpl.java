package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.artistPortfolio.ArtistPortfolio.DTO.ArtistProfileDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.ArtistProfileMediaDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.MediaDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.ProfileDTO;
import com.project.artistPortfolio.ArtistPortfolio.exception.ArtistNotFound;
import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.DataNotFound;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.exception.FileNotFound;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMedia;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMediaKey;
import com.project.artistPortfolio.ArtistPortfolio.model.Color;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.PaintingType;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.ArtistProfileRepository;
import com.project.artistPortfolio.ArtistPortfolio.repository.UserRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileMediaService;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileService;
import com.project.artistPortfolio.ArtistPortfolio.service.ColorService;
import com.project.artistPortfolio.ArtistPortfolio.service.MediaService;
import com.project.artistPortfolio.ArtistPortfolio.service.PaintingTypeService;
import com.project.artistPortfolio.ArtistPortfolio.service.UserService;

/**
 * This service class is used for handling all the operation related to artistProfile.
 * @author anju.kumari
 *
 *@version 1.0
 */
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
	private PaintingTypeService paintingTypeService;
	
	@Autowired
	private ArtistProfileMediaService artistProfileMediaService;
	
	@Autowired
	private ColorService colorService;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * This is used to get artist profile pic path by profile id
	 * @param id
	 * 			user id
	 * @return Media object
	 */
	public Media getProfilePicByArtistProfileId(int id) {
		try {
			
			ArtistProfile artistProfile = userService.getUserById(id).getArtistProfile();
			if(artistProfile==null) {
				throw new ArtistNotFound("Artist account has not been created! create first then proceed with uploading image.");
			}
			
			Media media = artistProfile.getMedia();
			
			if(media==null) {
				throw new FileNotFound("profile pic not uploaded!");
			}
			File a = new File(media.getPath());
			
			return artistProfile.getMedia();	
		}catch (FileNotFound e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile pic not uploaded");
		}catch (ArtistNotFound e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist account has not been created!create first then proceed with uploading image.");
		}
		
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile pic not loaded");
		}	 
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
	public ProfileDTO getArtistPublicProfileInfo(int artistProfileId) {
		
		try {
			ProfileDTO profileDTO = new ProfileDTO();
			
			ArtistProfile artistProfile = getArtistProfileById(artistProfileId);
			
			UserModel user = artistProfile.getUser();
			
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
			if(color!=null) {
				profileDTO.setColorName(color.getColorName());
			}
			
			return profileDTO;
		}catch (Exception e) {
			logger.info(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}	
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
		
		try {
			artistProfile.setAboutMe(artistProfileDTO.getAboutMe());
			
			artistProfile.setFacebookUrl(artistProfileDTO.getFacebookUrl());
			artistProfile.setLinkedinUrl(artistProfileDTO.getLinkedinUrl());
			artistProfile.setTwitterUrl(artistProfileDTO.getTwitterUrl());
			artistProfile.setProfileName(artistProfileDTO.getProfileName());
			
			ArtistProfile existingArtistProfile = getArtistProfileByProfileName(artistProfileDTO.getProfileName());
			if(existingArtistProfile!=null) {
				throw new DataNotFound("profile name already taken");
			}
			
			List<PaintingType> paintingTypesSet = new ArrayList<PaintingType>(); // empty list
			List<String> paintingTypeLists = artistProfileDTO.getPaintingType(); // input list of painting type
				
			if(paintingTypeLists!=null) {
				
				for (String paintingTypeList: paintingTypeLists) {
					
					PaintingType p = paintingTypeService.getPaintingTypeByPaintingName(paintingTypeList);
					paintingTypesSet.add(p);	
				}
				artistProfile.setPaintingType(paintingTypesSet); // list of painting type.
			}
			UserModel user = userService.getUserByEmail(artistProfileDTO.getEmail());
			artistProfile.setUser(user);
			
			Color existingColor = colorService.getColorByColorName(artistProfileDTO.getColorName());
			if(existingColor!=null) {
				artistProfile.setColorId(existingColor.getId());
			}
			
			// to create a folder by artist profile name
			Path existingPath = Paths.get("../ArtistPortfolioAPI/media/"+artistProfileDTO.getProfileName());
			boolean flag = Files.notExists(existingPath);
			if(!flag) {
				logger.info("folder exists");
			}else {
		
			File parentDir = new File("../ArtistPortfolioAPI/media/");
			File newDir = new File(parentDir, artistProfileDTO.getProfileName());
			newDir.mkdir();
			File thumbnail = new File("../ArtistPortfolioAPI/media/"+artistProfileDTO.getProfileName()+"/");
			File newThumbnailDir = new File(thumbnail,"thumbnail");
			newThumbnailDir.mkdir();
			
		}
		
		artistProfileRepository.save(artistProfile);	
		}catch (DataNotFound e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "profile name already taken");
		}
		catch(RuntimeException e){
			throw new CustomException(ExceptionMessage.Profile_Name_alreay_exists, HttpStatus.BAD_REQUEST);
			
		}
	}
	
	/**
	 * this is used to create media and link to artist profile
	 * @param List of MediaDTO
	 * @param profileName
	 * 			profile name of artist
	 * @throws IOException 
	 */
	@Override
	public void addArtistProfileMedia(MediaDTO mediaDTO,String profileName)  {
		
		Media media = new Media();
		
		// for renaming the fileName
		media.setFileName( "thumb"+mediaDTO.getFileName() );
		media.setFilenameOriginal( mediaDTO.getFileName());
		media.setPath(mediaDTO.getPath());
		media.setPathThumb(mediaDTO.getPath()+"thumbnail/");

		
		int mediaId = mediaService.createMedia(media).getId();
		
		ArtistProfile exsitingArtistProfile = artistProfileRepository.findByProfileName(profileName);
		int artistProfileId = exsitingArtistProfile.getId();
		
		ArtistProfileMediaDTO artistProfileMediaDTO = new ArtistProfileMediaDTO();
		
		artistProfileMediaDTO.setArtistProfileId(artistProfileId);
		artistProfileMediaDTO.setMediaId(mediaId);
		artistProfileMediaDTO.setPublicImage("false");
		
		artistProfileMediaDTO.setArtistProfileMediaKey( new ArtistProfileMediaKey(artistProfileId,mediaId));
		
		artistProfileMediaService.createArtistProfileMediaLink(artistProfileMediaDTO);	
	}
	
	/**
	 * This is used for updating the artist profile record
	 * 
	 * @param ArtistProfileDTO
	 * 
	 * @param email id
	 */
	@Override
	public void updateArtistProfileRecord(ArtistProfileDTO artistProfileDTO,String email) {
		
		try {
		UserModel user = userService.getUserByEmail(email);
		int artistId = user.getArtistProfile().getId();
		
		ArtistProfile existingRecord = getArtistProfileById(artistId);
		
		if(existingRecord!=null) {
			
			user.setFname(artistProfileDTO.getfName());
			user.setLname(artistProfileDTO.getlName());
			logger.info("-------------------------------");
			logger.info(artistProfileDTO.getlName());
			
			existingRecord.setAboutMe(artistProfileDTO.getAboutMe());
			existingRecord.setFacebookUrl(artistProfileDTO.getFacebookUrl());
			existingRecord.setLinkedinUrl(artistProfileDTO.getLinkedinUrl());
			existingRecord.setTwitterUrl(artistProfileDTO.getTwitterUrl());
			//existingRecord.setProfileName(artistProfileDTO.getProfileName());
			
			ArtistProfile existingArtistProfile = getArtistProfileByProfileName(artistProfileDTO.getProfileName());
			if(existingArtistProfile!=null) {
				throw new DataNotFound("profile name already taken");
			}
			
			List<PaintingType> paintingTypesSet = new ArrayList<PaintingType>(); // empty list
			List<String> paintingTypeLists = artistProfileDTO.getPaintingType(); // input list of painting type
			for (String paintingTypeList: paintingTypeLists) {
				
				PaintingType p = paintingTypeService.getPaintingTypeByPaintingName(paintingTypeList);
						
				paintingTypesSet.add(p);	
			}
			existingRecord.setPaintingType(paintingTypesSet); // list of painting type.
			
			Color existingColor = colorService.getColorByColorName(artistProfileDTO.getColorName());
			if(existingColor!=null) {
				existingRecord.setColorId(existingColor.getId());
			}
			existingRecord.setUser(user);
			
			artistProfileRepository.save(existingRecord);
			userRepository.save(user);
		}
		}catch (DataNotFound e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "profile name already taken");
		}catch (Exception e) {
			logger.info(e.getMessage());
		}
	}
	
	/**
	 * This is used to get ArtistProfile by artist id.
	 * @param id
	 * 			artistProfileID,
	 * 
	 * @return ArtistProfile object
	 */
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
	
	/**
	 * This is used to get artist by artist by email id
	 * 
	 * @param profile name
	 * 
	 * @return ArtistProfile object
	 */
	public ArtistProfile getArtistProfileByProfileName(String profileName) {
		
		try {
			return artistProfileRepository.findByProfileName(profileName);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * This is used to return all the id of artist
	 * @return List<Integer>
	 * 			list of artist profile id.
	 */
	public List<Integer> getAllArtistId(int pageNo,int pageLimit) {
		
		List<Integer> listOfIds =  new ArrayList<Integer>();
		
		Page<ArtistProfile> artistProfiles = artistProfileRepository.findAll((Pageable) PageRequest.of(pageNo, pageLimit));
		for(ArtistProfile artistProfile: artistProfiles) {
			
			int id = artistProfile.getId();
			listOfIds.add(id);
		}
		return listOfIds;
	}
	
	/***
	 * This is used to delete artist profile by id.
	 * 
	 * @param id
	 * 			artistProfileId.
	 */
	public String deleteByid(int id) {
		
		artistProfileRepository.deleteById(id);
		return "record has been deleted";
	}

}
