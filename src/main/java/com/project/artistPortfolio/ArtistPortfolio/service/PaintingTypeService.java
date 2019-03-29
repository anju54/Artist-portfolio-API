package com.project.artistPortfolio.ArtistPortfolio.service;

import java.util.List;

import com.project.artistPortfolio.ArtistPortfolio.model.PaintingType;

public interface PaintingTypeService {
	
	PaintingType getPaintingById(int id);
	void createPainting(PaintingType PaintingType);
	void updatePainting(PaintingType paintingType, int id);
	List<PaintingType> getAllPaintingType();
	void deletePaintingById(int id);

}
