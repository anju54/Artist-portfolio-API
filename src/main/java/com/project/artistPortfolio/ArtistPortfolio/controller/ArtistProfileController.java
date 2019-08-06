package com.project.artistPortfolio.ArtistPortfolio.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.project.artistPortfolio.ArtistPortfolio.DTO.ArtistProfileDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.MediaDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.ProfileDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.UserAsArtistDTO;
import com.project.artistPortfolio.ArtistPortfolio.exception.FileNotFound;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.PaintingType;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileService;
import com.project.artistPortfolio.ArtistPortfolio.service.UserService;

/**
 * This is used for mapping all the operation related to artist
 * @author anjuk
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/artist-profile")
public class ArtistProfileController {
	
	@Autowired
	ArtistProfileService artistProfileService;
	
	@Autowired
	private UserService userService;
	
	private final static Logger logger = LoggerFactory.getLogger(ArtistProfileController.class);
	
	/**
	 * This is used to get all the artist
	 * 
	 * @return List of artistProfile object.
	 */
	@GetMapping("/all")
	public List<UserAsArtistDTO> getAllArtistProfile() {
		
		return  artistProfileService.getAllArtistProfileFullName();
	}
	
	/**
	 * This is used to get artist profile pic path by profile id
	 * @param id
	 * @return Media object
	 */
	@GetMapping("/profile-pic")
	public Media getProfilePicByArtistProfileId(Authentication authentication) {
		
		String email = userService.getPrincipalUser(authentication).getUsername();
		UserModel user = userService.getUserByEmail(email);
		int id = user.getId();   // user id
		
		return artistProfileService.getProfilePicByArtistProfileId(id);
	}
	
	/**
	 * This is used to get ArtistProfile by artist id.
	 * @param id
	 * 			artistProfileID,
	 * 
	 * @return ArtistProfile object
	 */
	@GetMapping("/public/profile-pic")
	public Media getPublicProfilePic(@RequestParam("id") int artistProfileId) {
		
		Media media = artistProfileService.getArtistProfileById(artistProfileId).getMedia();
		return media;
	}
	
	/**
	 * This is used to get artist profile information that will be display over 
	 * artist profile page 
	 * @param artistProfileId
	 * 		artist Profile id.
	 * @param profileName
	 * 		artist profile name.
	 */
	@GetMapping("/info")
	public ProfileDTO getArtistProfile(@RequestParam("id") int artistProfileId) {
		
		return artistProfileService.getArtistPublicProfileInfo(artistProfileId);
	}
	
	@GetMapping("/secured/info/{email}")
	public ProfileDTO getArtistProfileData(@PathVariable("email") String email) {
		
		try {
			ArtistProfile artistProfile = userService.getUserByEmail(email).getArtistProfile();
			if(artistProfile==null) {
				throw new FileNotFound("artist not found!");
			}
			int artistProfileId = artistProfile.getId();
			return artistProfileService.getArtistPublicProfileInfo(artistProfileId);		
		}catch (FileNotFound e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Please enter your data to create an artist profile.");
		}
	}
	
	/**
	 * This is used to display all the painting type of any artist by id.
	 * @param id
	 * 		artist profile id.
	 */
	@GetMapping("/{id}/all/painting_type")
	public  List<PaintingType>  getArtistProfilePaintingByid(@PathVariable("id") int id) {
		
		return artistProfileService.getListOfPaintingType(id);
	}
	
	/**
	 * This is used to get ArtistProfile by artist id.
	 * @param id
	 * 			artistProfileID,
	 * 
	 * @return ArtistProfile object
	 */
	@GetMapping("/{id}")
	public ArtistProfile getArtistProfileByid(@PathVariable("id") int id) {
		
		return artistProfileService.getArtistProfileById(id);
	}
	
	/***
	 * This is used to delete artist profile by id.
	 * 
	 * @param id
	 * 			artistProfileId.
	 */
	@DeleteMapping("/{id}")
	public void deleteArtistProfile(int id) {
		
		artistProfileService.deleteByid(id);
	}
	
	/**
	 * This is used to create artist profile basic information with painting type
	 * @param ArtistProfileDTO
	 */
	@PostMapping("/basic-info")
	public int create(@RequestBody ArtistProfileDTO artistProfileDTO) {
		
		return artistProfileService.createArtistProfileRecord(artistProfileDTO);
	}
	
	/**
	 * this is used to create media and link to artist profile
	 * @param List of MediaDTO
	 * @param profileName
	 * 			profile name of artist
	 * @throws IOException 
	 */
	@PostMapping("/link/media")
	public void createLinkArtistProlfileMedia(@RequestBody MediaDTO mediaList,@RequestParam("profileName")
																					String profileName) {
		artistProfileService.addArtistProfileMedia(mediaList, profileName);
	}

	/**
	 * This is used for updating the artist profile record
	 * 
	 * @param ArtistProfileDTO
	 * 
	 * @param email id
	 */
	@PutMapping("/basic-info/{email}")
	public void update(@RequestBody ArtistProfileDTO artistProfileDTO,@PathVariable("email") String email) {
		
		artistProfileService.updateArtistProfileRecord(artistProfileDTO,email);
	}
	
	/**
	 * This is used for getting the artistId from user module if it is artist
	 * @param email
	 * @return artistProfileId.
	 */
	@GetMapping("/loggedIn/{email}")
	public int getArtistProfileId(@PathVariable("email") String email){
		int artistProfileId = 0;
		try {
			 artistProfileId =  userService.getUserByEmail(email).getArtistProfile().getId();
			return artistProfileId;
		}catch (Exception e) {
			logger.info(e.getMessage());
		}
		return artistProfileId;
		
	}
}
