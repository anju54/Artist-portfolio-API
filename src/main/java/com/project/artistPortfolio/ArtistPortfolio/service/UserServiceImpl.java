package com.project.artistPortfolio.ArtistPortfolio.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.artistPortfolio.ArtistPortfolio.DTO.CurrentUserDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.RegistrationDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.UpdateUserDTO;
import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.mail.EmailServiceImpl;
import com.project.artistPortfolio.ArtistPortfolio.model.Role;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.RoleRepository;
import com.project.artistPortfolio.ArtistPortfolio.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	 @Autowired
	 public JavaMailSender mailSender;
	 
	 @Autowired
	 private EmailServiceImpl emailService;
	 
	 @Autowired
	private LinksService linksService;
	 
	 @Autowired
	 private RoleRepository roleRepository;
	
	 /**
	 * This is used to display Artist profile information
	 * @param authentication
	 * @return ProfileDTO
	 * 			it holds the data related to artist profile
	 */
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
 
	/***
	 * This is used to get particular user by id
	 * 
	 * @param id
	 * 		user id
	 * @return UserModel
	 */
	@Override
	public UserModel getUserById( int id ) {
		
		try {
			Optional<UserModel> userOp = userRepository.findById(id);
			UserModel user = userOp.get();
			return user;
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * This is used for registration 
	 * 
	 * @param registrationDTO
	 * 			DTO for registration related data
	 * @return String message
	 */
	@Override
	public String createUser( RegistrationDTO registrationDTO ) {
		
		logger.info("trying to create account");
		
		UserModel user = new UserModel();
		mapDTOtoObject(user, registrationDTO);
		logger.info(user.getEmail());
		try {
			String email = user.getEmail();
			logger.info("checking if this user alreday exists? ");
			UserModel userOp = userRepository.findByEmail(email);
			
			if (userOp == null) {
				
				logger.info("proceeding to create new user");
				logger.info(registrationDTO.getRoleName());
				Role role = roleRepository.findByRole(registrationDTO.getRoleName());
				user.setRole(role);
				
				userRepository.save(user); 
				
				logger.info("data saved sucessfully in user table");
				
				String text = "set_password";
				String token = UUID.randomUUID().toString();
				
				linksService.createLinks(text, user.getEmail(), user.getId(),token); // create token 
				
				mailSender.send( emailService.registrationCredentialEmail(user, token) ); // send mail with token and set password link
				
				return "user registered succesfully";
			}
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
			//throw new CustomException(ExceptionMessage.DUPLICATE_email, HttpStatus.BAD_REQUEST);
		}
		return null;
	}
	
	/**
	 * this is used to update user personal information
	 * @param id
	 * 			user id
	 * @param UpdateUserDTO
	 * 		this is holding the user's personal information
	 */
	public void updateUser(int id,UpdateUserDTO updateUserDTO ) {
		
		UserModel existingUseruser = getUserById(id);
		if (existingUseruser!=null) {
			
			existingUseruser.setFname(updateUserDTO.getFname());
			existingUseruser.setLname(updateUserDTO.getLname());
			userRepository.save(existingUseruser);	
		}
	}
	
	/***
	 * This is used for deleting user object
	 * 
	 * @param id
	 * 		user id 
	 */
	@Override
	public void deleteUser(int id) {
		
		userRepository.deleteById(id);
		logger.info("data deleted from user table successfully!!");		
	}
	
	/***
	 * This is used for mapping dto to user object
	 * @param user
	 * @param registrationDTO
	 */
	public void mapDTOtoObject(UserModel user ,RegistrationDTO registrationDTO) {
		
		user.setEmail(registrationDTO.getEmail());
		user.setFname(registrationDTO.getFname());
		user.setLname(registrationDTO.getLname());
	}

	/***
	 * This is used to get list of user
	 * 
	 * @return list of user
	 */
	@Override
	public List<UserModel> getAllUser() {
		
		List<UserModel> users = userRepository.findAll();
		return users;
	}
	
	public UserModel getUserByEmail(String email) {
		
		try {
		return userRepository.findByEmail(email);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
	}

}
