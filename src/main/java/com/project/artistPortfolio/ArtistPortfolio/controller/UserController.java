package com.project.artistPortfolio.ArtistPortfolio.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.artistPortfolio.ArtistPortfolio.DTO.ProfileDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.RegistrationDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.UserRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/artist")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**
	 * This method maps the Post request to user, used to do user registration.
	 * 
	 * @param registrationDTO
	 *            to receive the incoming data
	 *
	 */
	@PostMapping("/registration")
	public @ResponseBody void createUser(@Valid @RequestBody RegistrationDTO registrationDTO) {
		userService.createUser(registrationDTO);
	}
	
	@GetMapping("/{id}")
	public @ResponseBody UserModel getUserByUserId(@PathVariable("id") int id) {
		
		return userService.getUserById(id);
	}
	
	/**
	 * This is used to display Artist profile information
	 * @param authentication
	 * @return ProfileDTO
	 * 			it holds the data related to artist profile
	 */
	@GetMapping("/username")
	public ProfileDTO getPrincipalUser(Authentication authentication) {
		
		logger.info("trying to get all detail of artist");
		ProfileDTO profileDTO = new ProfileDTO();

		String username =  authentication.getName(); // email id
		profileDTO.setEmail(username);
		
		UserModel user = userRepository.findByEmail(username);
		String name = user.getFname() + " " + user.getLname();
		profileDTO.setFullName(name);
		
		ArtistProfile artistProfile = user.getArtistProfile();
		profileDTO.setAboutMe(artistProfile.getAboutMe());
		profileDTO.setFacebookUrl(artistProfile.getFacebookUrl());
		profileDTO.setLinkedinUrl(artistProfile.getLinkedinUrl());
		profileDTO.setTwitterUrl(artistProfile.getTwitterUrl());
		profileDTO.setProfileName(artistProfile.getProfileName());
		profileDTO.setPaintingType(artistProfile.getPaintingType());
		
		return profileDTO;
	}
	
}
