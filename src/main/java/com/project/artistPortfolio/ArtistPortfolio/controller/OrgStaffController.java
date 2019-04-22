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

import com.project.artistPortfolio.ArtistPortfolio.DTO.OrgStaffDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.OrgStaff;
import com.project.artistPortfolio.ArtistPortfolio.service.OrgStaffService;

/**
 * This is used for mapping all the operation related to orgStaff
 * @author anjuk
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/orgStaff")
public class OrgStaffController {
	
	@Autowired
	private OrgStaffService orgStaffService;
	
	/**
	 * This is used to create new orgStaff.
	 * 
	 * @param orgStaff object
	 */
	@PostMapping("/")
	public void createNeworgStaff(@RequestBody OrgStaffDTO orgStaffDTO) {
		
		
		orgStaffService.addOrgStaff(orgStaffDTO);
	}
	
	/**
	 * This is used to update new orgStaff.
	 * 
	 * @param id
	 * 			orgStaff id.
	 * @param orgStaff object.
	 */
	@PutMapping("/{id}")
	public void updateorgStaff(@PathVariable("id") int id, @RequestBody OrgStaff orgStaff) {
		orgStaffService.updateOrgStaff(id, orgStaff);
	}
	
	/**
	 * This is used to get orgStaff by id.
	 * 
	 * @param id
	 * 			orgStaff id.
	 * @return orgStaff object.
	 */
	@GetMapping("/{id}")
	public OrgStaff getorgStaffByid(@PathVariable("id") int id) {
		
		return orgStaffService.getOrgStaffById(id);
	}
	
	/**
	 * This is used to get orgStaff by id.
	 * 
	 * @return List<orgStaff>.
	 */
	@GetMapping("/all")
	public List<OrgStaff> getAllorgStaff(int id){
		return orgStaffService.getallOrgStaff();
	}

}
