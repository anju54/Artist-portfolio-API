package com.project.artistPortfolio.ArtistPortfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMedia;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMediaKey;

@Repository
public interface ArtistProfileMediaRepository extends JpaRepository<ArtistProfileMedia, ArtistProfileMediaKey> {
	
	List<ArtistProfileMedia> findArtistProfileMediaByArtistProfileId(int id); // id is artistProfileId 
	
}
