package com.project.artistPortfolio.ArtistPortfolio.service;

import java.util.List;

import com.project.artistPortfolio.ArtistPortfolio.DTO.ArtistProfileDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.model.PaintingType;

public interface ArtistProfileService {
	
	//void processArtistProfile(ArtistProfile artistProfile);
	void createArtistProfileRecord(ArtistProfileDTO artistProfileDTO);
	void updateArtistProfileRecord(ArtistProfile artistProfile, int id);
	ArtistProfile getArtistProfileById(int id);
	String deleteByid(int id);
	List<PaintingType> getListOfPaintingType(int id);
	
	//void addPainintingType(List<PaintingTypeDTO> paintingTypeDTOs, int id);

}
