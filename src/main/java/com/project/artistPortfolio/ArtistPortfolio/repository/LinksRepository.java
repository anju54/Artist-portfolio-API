package com.project.artistPortfolio.ArtistPortfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.artistPortfolio.ArtistPortfolio.model.Links;

public interface LinksRepository extends JpaRepository<Links, Integer>{
	
	Links findByToken(String token);

}
