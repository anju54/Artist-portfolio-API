package com.project.artistPortfolio.ArtistPortfolio.service;

import com.project.artistPortfolio.ArtistPortfolio.model.Links;

/***
 * This interface is implemented by LinkSServiceImpl to do operation related to 
 * generate token
 * @author anjuk
 *
 */
public interface LinksService {
	
	void createLinks(String text,  String email, int id);
	Links getLinksById(int id);

}
