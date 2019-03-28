package com.project.artistPortfolio.ArtistPortfolio.controller;

import java.util.Calendar;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.artistPortfolio.ArtistPortfolio.model.Links;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.LinksRepository;
import com.project.artistPortfolio.ArtistPortfolio.repository.UserRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.UserService;

@RestController
@CrossOrigin
public class PasswordController {
	
	@Autowired
	private LinksRepository linksRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	private final static Logger logger = LoggerFactory.getLogger(PasswordController.class);
	
//	@PostMapping("/link/{text}/{email}/{id}")
//	public boolean createToken(@PathVariable("text") String text, @PathVariable("email")
//							String email, @PathVariable("id") int id) {
//		
//		logger.info("trying to create token for set-password");
//		linksService.createLinks(text,email,id);	
//		return true;
//	}
	
	/**
	 * This method is used to validate the token
	 * 
	 * @param id
	 * 			user id
	 * @param token
	 * 			token
	 * @return true ( if all the condition passed ).
	 */
	public boolean validatePasswordResetToken(long id, String token) {

		Links passwordToken = linksRepository.findByToken(token);
		
		if ((passwordToken == null) || (passwordToken.getRefrenceId()!= id)) {
			logger.info("token or user id is not matched");
		        return false;
		    }
		
		Calendar cal = Calendar.getInstance();
	    if ((passwordToken.getExpiryDate()
	        .getTime() - cal.getTime()
	        .getTime()) < 0) {
	    	logger.info("expired");
	    	System.out.println("expiry date table "+ passwordToken.getExpiryDate());
	    	System.out.println(" current time " + cal.getTime() );	    	
	        return false;
	    }
	return true;
	}	 
	
	/***
	 * this method is used to set the password for the first time
	 * @param id
	 * @param token
	 * @param password
	 * @return String message
	 */
	@RequestMapping(value = "/api/user/set_password/valid/{token}/{id}", method = RequestMethod.POST)
	public String showSetPasswordPage( @PathVariable("token") String token, @PathVariable("id") int id,
			@RequestBody Map<String, String> password) {
		
		logger.info("calling from password controller to set password");
		
		boolean result = validatePasswordResetToken(id, token);
		if(result) {
			UserModel user = userService.getUserById(id);
			user.setPassword(passwordEncoder.encode(password.get("password")));
			userRepository.save(user);
			return "password has been created";
		}
		return null;
	}

}
