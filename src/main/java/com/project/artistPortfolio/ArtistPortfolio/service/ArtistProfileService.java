package com.project.artistPortfolio.ArtistPortfolio.service;

import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;

public interface ArtistProfileService {
	
	//void processArtistProfile(ArtistProfile artistProfile);
	void createArtistProfileRecord(ArtistProfile artistProfile);
	void updateArtistProfileRecord(ArtistProfile artistProfile, int id);
	ArtistProfile getArtistProfileById(int id);
	String deleteByid(int id);

}
