package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.Invitation;
import com.project.artistPortfolio.ArtistPortfolio.model.Organization;
import com.project.artistPortfolio.ArtistPortfolio.repository.InvitationRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.InvitationService;
import com.project.artistPortfolio.ArtistPortfolio.service.OrganizationService;

@Service
public class InvitationServiceImpl implements InvitationService{
	
	private final static Logger logger = LoggerFactory.getLogger(InvitationServiceImpl.class);
		
	@Autowired
	private InvitationRepository invitationRepository;
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * This is used to get Invitation by id.
	 * 
	 * @param id
	 * 			organization id.
	 * @return Invitation object.
	 */
	@Override
	public Invitation getInvitationByName(String name) {
		
		return null;
	}
	
	/**
	 * This is used to create new organization.
	 * 
	 * @param Invitation object
	 */
	public void addInvitation(Invitation invitation) {
		
		invitationRepository.save(invitation);
	}
	
	/**
	 * This is used to update new organization.
	 * 
	 * @param id
	 * 			organization id.
	 * @param Invitation object.
	 */
	public void updateInvitation(int id,Invitation organization) {
		
		
	}
	
	/**
	 * This is used to get Invitation by id.
	 * 
	 * @param id
	 * 			organization id.
	 * @return Invitation object.
	 */
	public Invitation getInvitationById(int id) {
		
		try {
			
			Optional<Invitation> invitation =  invitationRepository.findById(id);
			Invitation exh = invitation.get();;
			return exh;
		}catch (Exception e) {
			logger.info(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
		 
	}
	
	/**
	 * This is used to get all Invitation.
	 * 
	 * @return List<Invitation>.
	 */
	public List<Invitation> allInvitation(){
		
		 List<Invitation> allInvitation = invitationRepository.findAll();
		 return allInvitation;
	}
	
	
	/**
	 * This is used for deleting the organization.
	 */
	public boolean deleteInvitation(int id) {
		
		invitationRepository.deleteById(id);
		return true;	
	}

	
}
