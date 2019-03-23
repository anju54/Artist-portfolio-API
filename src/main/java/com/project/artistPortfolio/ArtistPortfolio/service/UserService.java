package com.project.artistPortfolio.ArtistPortfolio.service;

import java.util.List;

import com.project.artistPortfolio.ArtistPortfolio.DTO.RegistrationDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;

/**
 * @author anjuk
 *
 */
public interface UserService {
	
	List<UserModel> getAllUser();
	UserModel getUserById(int id);
	//void updateUser(Long id,UserPersonalDTO userDTO);
	void deleteUser(int id);
	String createUser(RegistrationDTO registrationDTO);
	

}
