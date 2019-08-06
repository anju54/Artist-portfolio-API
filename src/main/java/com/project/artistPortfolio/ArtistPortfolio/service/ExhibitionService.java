package com.project.artistPortfolio.ArtistPortfolio.service;

import java.util.List;

import com.project.artistPortfolio.ArtistPortfolio.DTO.Response;
import com.project.artistPortfolio.ArtistPortfolio.model.Exhibition;

public interface ExhibitionService {
	
	Response<Exhibition> addExhibition(String title,String organization);
	Response<Exhibition> updateExhibition(int id,Exhibition exhibition);
	Exhibition getExhibitionById(int id);
	Exhibition getExhibitionByTitle(String title) ;
	List<Exhibition> allExhibition();
	boolean deleteExhibition(int id);
	List<Exhibition> allExhibitionByOrgId(int orgId);
}
