package com.project.artistPortfolio.ArtistPortfolio.service;

import java.util.List;

import com.project.artistPortfolio.ArtistPortfolio.DTO.OrganizationDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.Organization;

public interface OrganizationService {
	
	void addOrganization(OrganizationDTO organizationDTO,String email );
	void updateOrganization(int id,Organization organization);
	Organization getOrganizationById(int id);
	Organization getOrganizationByName(String name);
	List<Organization> allOrganization();
	void deleteOrganization(int id);

}
