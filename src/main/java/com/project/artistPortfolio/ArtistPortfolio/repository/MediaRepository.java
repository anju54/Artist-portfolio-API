package com.project.artistPortfolio.ArtistPortfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.artistPortfolio.ArtistPortfolio.model.Media;

public interface MediaRepository extends JpaRepository<Media, Integer>{
	
	Media getMediaByFileName(String fileName);

}
