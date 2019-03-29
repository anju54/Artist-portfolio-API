package com.project.artistPortfolio.ArtistPortfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.artistPortfolio.ArtistPortfolio.model.PaintingType;

public interface PaintingTypeRepository  extends JpaRepository<PaintingType, Integer>{
	
	PaintingType findPaintingTypeByPaintingName(String paintingName);

}
