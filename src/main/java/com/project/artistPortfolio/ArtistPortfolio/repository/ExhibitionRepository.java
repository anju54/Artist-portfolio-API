package com.project.artistPortfolio.ArtistPortfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.artistPortfolio.ArtistPortfolio.model.Exhibition;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Integer>{
	
	Exhibition findByTitle(String title);

}
