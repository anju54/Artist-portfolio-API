package com.project.artistPortfolio.ArtistPortfolio.service;

import java.util.List;

import com.project.artistPortfolio.ArtistPortfolio.DTO.InvitationDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.Invitation;

public interface InvitationService {
	
	void addInvitation(InvitationDTO invitationDTO);
	void updateInvitation(int id,Invitation invitation);
	Invitation getInvitationById(int id);
	Invitation getInvitationByName(String name);
	List<Invitation> allInvitation();
	boolean deleteInvitation(int id);
	
}
