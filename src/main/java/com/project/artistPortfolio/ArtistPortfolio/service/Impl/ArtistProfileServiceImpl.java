package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.repository.ArtistProfileRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileService;

@Service
public class ArtistProfileServiceImpl implements ArtistProfileService{
	
	private final static Logger logger = LoggerFactory.getLogger(ArtistProfileServiceImpl.class);
	
	@Autowired
	private ArtistProfileRepository artistProfileRepository;
	
	@Override
	public void createArtistProfileRecord(ArtistProfile artistProfile) {
		
		artistProfileRepository.save(artistProfile);	
	}
	
	@Override
	public void updateArtistProfileRecord(ArtistProfile artistProfile, int id) {
		
		ArtistProfile existingRecord = getArtistProfileById(id);
		
		if(existingRecord!=null) {
			
			existingRecord.setAboutMe(artistProfile.getAboutMe());
			existingRecord.setFacebookUrl(artistProfile.getFacebookUrl());
			existingRecord.setLinkedinUrl(artistProfile.getLinkedinUrl());
			existingRecord.setTwitterUrl(artistProfile.getTwitterUrl());
			existingRecord.setProfileName(artistProfile.getProfileName());
			
			artistProfileRepository.save(existingRecord);
		}
	}
	
	@Override
	public ArtistProfile getArtistProfileById(int id) {
		
		try {
			
			ArtistProfile artistProfile = artistProfileRepository.findById(id).get();
			return artistProfile;
		}catch (Exception e) {
				logger.error(e.getMessage());
				throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
	}
	
	public String deleteByid(int id) {
		
		artistProfileRepository.deleteById(id);
		return "record has been deleted";
	}

}
