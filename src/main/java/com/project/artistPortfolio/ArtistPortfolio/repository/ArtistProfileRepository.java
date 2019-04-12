package com.project.artistPortfolio.ArtistPortfolio.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;

@Repository
public interface ArtistProfileRepository extends PagingAndSortingRepository<ArtistProfile, Integer>{
	
	ArtistProfile findByProfileName(String profileName);

}
