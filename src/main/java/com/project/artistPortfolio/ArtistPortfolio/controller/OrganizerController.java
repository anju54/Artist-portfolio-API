package com.project.artistPortfolio.ArtistPortfolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.artistPortfolio.ArtistPortfolio.DTO.RegistrationDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.Organization;
import com.project.artistPortfolio.ArtistPortfolio.model.Organizer;
import com.project.artistPortfolio.ArtistPortfolio.repository.OrganizerRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.OrganizerService;

/**
 * This is used for mapping all the operation related to organizer
 * @author anjuk
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/organizer")
public class OrganizerController {
	
	@Autowired
	private OrganizerService organizerService;
	
	@Autowired
	private OrganizerRepository orgRepo;
	
	@GetMapping("/{id}/all")
	public List<Organizer> testing(@PathVariable("id") int id) {
		
		return orgRepo.findAllOrganizerByorganizationId(id);
		
	}
	
	/**
	 * This is used to get organization detail by organizer id
	 * @param id
	 * 			organizer id
	 * @return Organization object;
	 */
	@GetMapping("/{id}/organization")
	public Organization getOrganization(@PathVariable("id") int id) {
		
		return organizerService.getOrganizationByOrganizerId(id);
	}
	
	/**
	 * This is used to get Organizer id by token
	 * @return organizer id
	 */
	@GetMapping("/id")
	public int getOrganizerId(Authentication authentication) {
		
		return organizerService.getOrganizerIdbytoken(authentication);
	}
	
	/**
	 * This is used to create new organizer.
	 * 
	 * @param organizer object
	 */
	@PostMapping("/new")
	public void createNeworganizer(@RequestParam("organization") String organizationName,Authentication authentication) {
		
		organizerService.addOrganizer(organizationName,authentication);
	}
	
	@PostMapping("/user")
	public void adduserAsOrganizer( @RequestBody RegistrationDTO registrationDTO,@RequestParam("organization") String organizationName,
																	Authentication authentication) {
		
		organizerService.adduserAsOrganizer(registrationDTO, organizationName, authentication);
	}
	
	/**
	 * This is used to update new organizer.
	 * 
	 * @param id
	 * 			organizer id.
	 * @param organizer object.
	 */
	@PutMapping("/{id}")
	public void updateorganizer(@PathVariable("id") int id, @RequestBody Organizer organizer) {
		organizerService.updateOrganizer(id, organizer);
	}
	
	/**
	 * This is used to get organizer by id.
	 * 
	 * @param id
	 * 			organizer id.
	 * @return organizer object.
	 */
	@GetMapping("/{id}")
	public Organizer getorganizerByid(@PathVariable("id") int id) {
		
		return organizerService.getOrganizerById(id);
	}
	
	/**
	 * This is used to get organizer by id.
	 * 
	 * @return List<organizer>.
	 */
	@GetMapping("/all")
	public List<Organizer> getAllorganizer(int id){
		return organizerService.allOrganizer();
	}

}
