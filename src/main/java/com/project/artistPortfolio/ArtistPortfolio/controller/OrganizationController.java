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

import com.project.artistPortfolio.ArtistPortfolio.model.Organization;
import com.project.artistPortfolio.ArtistPortfolio.service.OrganizationService;

/**
 * This is used for mapping all the operation related to Organization
 * @author anju.kumari
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/organization")
public class OrganizationController {
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * This is used to create new organization.
	 * 
	 * @param Organization object
	 */
	@PostMapping("/")
	public void createNewOrganization(@RequestBody Organization organization,Authentication authentication) {
		
		organizationService.addOrganization(organization,authentication);
	}
	
	/**
	 * This is used to update new organization.
	 * 
	 * @param id
	 * 			organization id.
	 * @param Organization object.
	 */
	@PutMapping("/{id}")
	public void updateOrganization(@PathVariable("id") int id, @RequestBody Organization organization) {
		organizationService.updateOrganization(id, organization);
	}
	
	/**
	 * This is used to get Organization by id.
	 * 
	 * @param id
	 * 			organization id.
	 * @return Organization object.
	 */
	@GetMapping("/{id}")
	public Organization getOrganizationByid(@PathVariable("id") int id) {
		
		return organizationService.getOrganizationById(id);
	}
	
	/**
	 * This is used to get Organization by id.
	 * 
	 * @return List<Organization>.
	 */
	@GetMapping("/all")
	public List<Organization> getAllOrganization(int id){
		return organizationService.allOrganization();
	}

}
