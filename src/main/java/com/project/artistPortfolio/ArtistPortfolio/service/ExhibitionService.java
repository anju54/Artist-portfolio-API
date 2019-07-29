package com.project.artistPortfolio.ArtistPortfolio.service;

import java.util.List;

import com.project.artistPortfolio.ArtistPortfolio.model.Exhibition;

public interface ExhibitionService {
	
	void addExhibition(String title,String organization);
	void updateExhibition(int id,Exhibition exhibition);
	List<Exhibition> exhibitionByOrgId(int id);
	Exhibition getExhibitionById(int id);
	Exhibition getExhibitionByName(String name);
	List<Exhibition> allExhibition();
	boolean deleteExhibition(int id);
	List<Exhibition> allExhibitionByOrgId(int orgId);
}
