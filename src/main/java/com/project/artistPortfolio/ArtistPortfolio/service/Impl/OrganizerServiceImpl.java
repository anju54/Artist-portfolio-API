package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.Organization;
import com.project.artistPortfolio.ArtistPortfolio.model.Organizer;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.OrganizerRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.OrganizationService;
import com.project.artistPortfolio.ArtistPortfolio.service.OrganizerService;
import com.project.artistPortfolio.ArtistPortfolio.service.UserService;

@Service
public class OrganizerServiceImpl implements OrganizerService{
	
	private final static Logger logger = LoggerFactory.getLogger(LinksServiceImpl.class);
	
	@Autowired
	private OrganizerRepository organizerRepository;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * This is used to create new organizer.
	 * 
	 * @param OrganizerDTO 
	 */
	@Override
	public int addOrganizer(String organizationName,Authentication authentication) {
		
		logger.info("trying to create organizer for "+organizationName);
		
		Organizer organizer = new Organizer();
		
		int organizationId = organizationService.
				getOrganizationByName(organizationName).getOrganizationId();
		
		UserModel user = userService.getUserByEmail( ( userService.getPrincipalUser(authentication).getUsername() ) );
		
		organizer.setOrganizationId(organizationId);
		organizer.setUser(user);
		organizerRepository.save(organizer);	
		
		logger.info("organizer has been created now returning id");
		System.out.println(organizationId);;
		return organizationId;
	}
	
	/**
	 * This is used to update new organizer.
	 * 
	 * @param id
	 * 			organizer id.
	 * @param Organizer object.
	 */
	public void updateOrganizer(int id,Organizer organizer) {
		
//		Organizer existingOrganizer = getOrganizerById(id);
//		organizerRepository.save(existingOrganizer);
	}
	
	/**
	 * This is used to get Organizer by id.
	 * 
	 * @param id
	 * 			organizer id.
	 * @return Organizer object.
	 */
	@Override
	public Organizer getOrganizerById(int id) {
		
		try {
			Organizer existingOrganizer = organizerRepository.findById(id).get();
			return existingOrganizer;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * This is used to get organization detail by organizer id
	 * @param id
	 * 			organizer id
	 * @return Organization object;
	 */
	public Organization getOrganizationByOrganizerId(int id) {  //organizer id
		
		Organizer organizer = getOrganizerById(id);
		Organization org = organizationService.getOrganizationById(organizer.getOrganizationId());
		
		return org;
		
	}
	
	/**
	 * This is used to get Organizer id by token
	 * @return organizer id
	 */
	public int getOrganizerIdbytoken(Authentication authentication) {
		
		try {
		UserModel user = userService.getUserByEmail( userService.getPrincipalUser(authentication).getUsername() );
		return user.getOrganizer().getOrganizerId();
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Organizer not found!!");
		}
	}
	
	/**
	 * This is used to get Organizer by id.
	 * 
	 * @return List<Organizer>.
	 */
	public List<Organizer> allOrganizer(){
		
		return organizerRepository.findAll();
	}
	
	/**
	 * This is used for deleting the organizer.
	 */
	public void deleteOrganizer(int id) {
		
		organizerRepository.deleteById(id);
	}

	
}
