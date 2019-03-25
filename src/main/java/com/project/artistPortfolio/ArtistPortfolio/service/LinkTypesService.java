package com.project.artistPortfolio.ArtistPortfolio.service;

import com.project.artistPortfolio.ArtistPortfolio.model.LinkTypes;

/***
 * This interface is implemented by LinkTypesServiceImpl to do operation related to 
 * selecting types of links
 * @author anjuk
 *
 */

public interface LinkTypesService {
	
	LinkTypes createLinkTypes(LinkTypes linkTypes);
	LinkTypes getLinksTypesById(int id);
	

}
