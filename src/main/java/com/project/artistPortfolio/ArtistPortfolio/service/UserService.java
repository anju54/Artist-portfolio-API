package com.project.artistPortfolio.ArtistPortfolio.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.project.artistPortfolio.ArtistPortfolio.DTO.CurrentUserDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.RegistrationDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.UpdateUserDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;

/**
 * UserService Interface contains operatio related to user
 * @author anjuk
 *
 */
public interface UserService {
	
	List<UserModel> getAllUser();
	UserModel getUserById(int id);
	void updateUser(int id,UpdateUserDTO updateUserDTO );
	void deleteUser(int id);
	String createUser(RegistrationDTO registrationDTO);
	UserModel getUserByEmail(String email);
	CurrentUserDTO getPrincipalUser(Authentication authentication);

}
