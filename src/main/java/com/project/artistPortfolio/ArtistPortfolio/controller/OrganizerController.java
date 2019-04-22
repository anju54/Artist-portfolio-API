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
import org.springframework.web.bind.annotation.RestController;

import com.project.artistPortfolio.ArtistPortfolio.DTO.OrganizerDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.Organizer;
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
	
	/**
	 * This is used to create new organizer.
	 * 
	 * @param organizer object
	 */
	@PostMapping("/")
	public void createNeworganizer(@RequestBody OrganizerDTO organizerDTO,Authentication authentication) {
		
		organizerService.addOrganizer(organizerDTO,authentication);
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
