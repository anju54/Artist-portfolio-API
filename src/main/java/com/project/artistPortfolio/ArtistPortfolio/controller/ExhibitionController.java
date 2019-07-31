package com.project.artistPortfolio.ArtistPortfolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.artistPortfolio.ArtistPortfolio.DTO.Response;
import com.project.artistPortfolio.ArtistPortfolio.model.Exhibition;
import com.project.artistPortfolio.ArtistPortfolio.service.ExhibitionService;

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
	 public Response<Exhibition> createNewOrganization(@PathVariable("title") String title,@PathVariable("organization") String organization) {
		
		return exhibitionService.addExhibition(title, organization);
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
	 public Response<Exhibition> updateExhibition(@PathVariable("id") int id, @RequestBody Exhibition exhibition) {
		
		return exhibitionService.updateExhibition(id, exhibition);
		//return new ResponseEntity<Exhibition>(exhibition, HttpStatus.OK);
		
	}

	/** 
	 * This is used to get  Exhibition by exhibition id.
	 * 
	 * @param orgId
	 * 			exhibition id.
	 * 
	 * @return Exhibition object.
	 */
	 @GetMapping("/{id}")
	 public Exhibition exhibitionByExhibitionId(@PathVariable("id") int id){
		 
		 return exhibitionService.getExhibitionById(id);
	 }
	 
	 /**
	  * This is used to delete exhibition by id
	  * @param id
	  * 	exhibition id
	  * @return true or false.
	  */
	 @DeleteMapping("/{id}")
	 public boolean deleteExhById(@PathVariable("id") int id) {
		 
		 return exhibitionService.deleteExhibition(id);
		 
	 }
}

