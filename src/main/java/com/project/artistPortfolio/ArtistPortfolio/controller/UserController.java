package com.project.artistPortfolio.ArtistPortfolio.controller;

import javax.validation.Valid;

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

import com.project.artistPortfolio.ArtistPortfolio.DTO.RegistrationDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.UserRepository;
import com.project.artistPortfolio.ArtistPortfolio.security.JwtTokenUtil;
import com.project.artistPortfolio.ArtistPortfolio.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/artist")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
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
	
	@GetMapping("/username")
	public String getPrincipalUser(Authentication authentication) {
		
		String username =  authentication.getName();
		UserModel user = userRepository.findByEmail(username);
		String name = user.getFname() + " " + user.getLname();
		return name;
	}
	
}
