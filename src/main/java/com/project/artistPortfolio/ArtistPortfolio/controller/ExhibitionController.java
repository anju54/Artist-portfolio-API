package com.project.artistPortfolio.ArtistPortfolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.artistPortfolio.ArtistPortfolio.DTO.OrganizationDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.Exhibition;
import com.project.artistPortfolio.ArtistPortfolio.service.ExhibitionService;
import com.project.artistPortfolio.ArtistPortfolio.service.OrganizationService;

/**
 * This is used for mapping all the operation related to Exhibition
 * @author anju.kumari
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/exhibition")
public class ExhibitionController {
	
	@Autowired
	private ExhibitionService exhibitionService;
	
	/**
	 * This is used to create new exhibition.
	 * 
	 * @param Exhibition object
	 */
	@PostMapping("/{title}/{organization}")
	public void createNewOrganization(@PathVariable("title") String title,@PathVariable("organization") String organization) {
		
		exhibitionService.addExhibition(title, organization);
	}
	
	/**
	 * This is used to get all Exhibition by org id.
	 * 
	 * @param orgId
	 * 			organization id.
	 * 
	 * @return List<Exhibition>.
	 */
	 @GetMapping("/org/{id}")
	 public List<Exhibition> exhibitionByOrgId(@PathVariable("id") int id){
		 
		 return exhibitionService.allExhibitionByOrgId(id);
	 }
	
	/**
	 * This is used to update  exhibition.
	 * 
	 * @param id
	 * 			exhibition id.
	 * @param Exhibition object.
	 */
	@PutMapping("/{id}")
	public void updateOrganization(@PathVariable("id") int id, @RequestBody Exhibition exhibition) {
		
	}

}
