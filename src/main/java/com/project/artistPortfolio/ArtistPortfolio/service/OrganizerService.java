package com.project.artistPortfolio.ArtistPortfolio.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.project.artistPortfolio.ArtistPortfolio.model.Organization;
import com.project.artistPortfolio.ArtistPortfolio.model.Organizer;

public interface OrganizerService {
	
	void addOrganizer(String organizationName,Authentication authentication );
	void updateOrganizer(int id,Organizer organizer);
	Organizer getOrganizerById(int id);
	List<Organizer> allOrganizer();
	void deleteOrganizer(int id);
	Organization getOrganizationByOrganizerId(int id);
	int getOrganizerIdbytoken(Authentication authentication);

}
