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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.artistPortfolio.ArtistPortfolio.DTO.CurrentUserDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.RegistrationDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.UpdateUserDTO;
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
	
	@PutMapping("/{id}")
	public @ResponseBody void updateUser(@PathVariable("id") int id, @Valid @RequestBody UpdateUserDTO updateUserDTO) {
		userService.updateUser(id, updateUserDTO);
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
	public CurrentUserDTO getPrincipalUser(Authentication authentication) {
		
		try {
		logger.info("trying to get all detail of artist");
		CurrentUserDTO currentUserDTO = new CurrentUserDTO();
		
		String username =  authentication.getName(); // email id
		UserModel user = userRepository.findByEmail(username);
		String name = user.getFname() + " " + user.getLname();
		
		currentUserDTO.setFullName(name);
		currentUserDTO.setUsername(username);
		return currentUserDTO;
		
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}
		return null;
	}
	
}
