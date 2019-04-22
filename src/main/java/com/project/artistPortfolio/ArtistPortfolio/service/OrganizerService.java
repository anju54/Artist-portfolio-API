package com.project.artistPortfolio.ArtistPortfolio.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.project.artistPortfolio.ArtistPortfolio.DTO.OrganizerDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.Organizer;

public interface OrganizerService {
	
	void addOrganizer(OrganizerDTO organizerDTO,Authentication authentication );
	void updateOrganizer(int id,Organizer organizer);
	Organizer getOrganizerById(int id);
	List<Organizer> allOrganizer();
	void deleteOrganizer(int id);

}
