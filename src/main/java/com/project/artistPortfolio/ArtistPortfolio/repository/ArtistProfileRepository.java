package com.project.artistPortfolio.ArtistPortfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;

@Repository
public interface ArtistProfileRepository extends JpaRepository<ArtistProfile, Integer>{
	
	ArtistProfile findByProfileName(String profileName);

}
