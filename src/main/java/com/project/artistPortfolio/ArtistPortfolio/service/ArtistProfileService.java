package com.project.artistPortfolio.ArtistPortfolio.service;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import com.project.artistPortfolio.ArtistPortfolio.DTO.ArtistProfileDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.MediaDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.ProfileDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.PaintingType;

public interface ArtistProfileService {
	
	//void processArtistProfile(ArtistProfile artistProfile);
	void createArtistProfileRecord(ArtistProfileDTO artistProfileDTO);
	void updateArtistProfileRecord(ArtistProfileDTO artistProfileDTO, String email);
	ArtistProfile getArtistProfileById(int id);
	String deleteByid(int id);
	List<PaintingType> getListOfPaintingType(int id);
	
	public void addArtistProfileMedia(MediaDTO mediaDTO,String profileName);
	
	ArtistProfile getArtistProfileByProfileName(String profileName);
	
	ProfileDTO getArtistPublicProfileInfo(Authentication authentication);
	
	Media getProfilePicByArtistProfileId(int id);
	
	
	
	//void addPainintingType(List<PaintingTypeDTO> paintingTypeDTOs, int id);

}
