package com.project.artistPortfolio.ArtistPortfolio.service;

import java.util.List;

import com.project.artistPortfolio.ArtistPortfolio.DTO.ArtistProfileDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.MediaDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.ProfileDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.UserAsArtistDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.PaintingType;

/**
 * This is used to handle all the operation related to ArtistProfile
 * @author anjuk
 *
 */
public interface ArtistProfileService {
	
	int createArtistProfileRecord(ArtistProfileDTO artistProfileDTO);
	void updateArtistProfileRecord(ArtistProfileDTO artistProfileDTO, String email);
	ArtistProfile getArtistProfileById(int id);
	String deleteByid(int id);
	List<PaintingType> getListOfPaintingType(int id);
	
	public void addArtistProfileMedia(MediaDTO mediaDTO,String profileName);
	
	ArtistProfile getArtistProfileByProfileName(String profileName);
	
	ProfileDTO getArtistPublicProfileInfo(int artistProfileId);
	
	Media getProfilePicByArtistProfileId(int id);
	
	List<Integer> getAllArtistId(int pageNo,int pageLimit);
	
	List<UserAsArtistDTO> getAllArtistProfileFullName();

}
