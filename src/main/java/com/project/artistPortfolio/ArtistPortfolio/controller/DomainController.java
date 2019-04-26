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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.artistPortfolio.ArtistPortfolio.model.OrganizationDomain;
import com.project.artistPortfolio.ArtistPortfolio.service.DomainService;

@RestController
@CrossOrigin
@RequestMapping("/api/domain")
public class DomainController {
	
	@Autowired
	private DomainService domainService;
	
	@GetMapping("/all")
	public List<OrganizationDomain> getAllDomain(){
		
		return domainService.getAllDomains();
	}
	
	/**
	 * This is used to get domain by organization id
	 * @param id
	 * 			organization id
	 * @return List of OrganizationDomain 
	 */
	@GetMapping("/organization/{id}")
	public List<OrganizationDomain> getDomainByOrganizatioId(@PathVariable("id") int id){
		
		return domainService.getDomainByOrganizatioId(id);
	}
	
	@GetMapping("/{id}")
	public @ResponseBody OrganizationDomain getDomainById(@PathVariable("id") int id) {
		
		return domainService.getDomainById(id);
	}
	
	@PostMapping("/organization/{id}")
	public void create(@RequestBody OrganizationDomain domain,@PathVariable("id") int id) {
		
		domainService.create(domain,id);
	}
	
	@PutMapping("/{id}")
	public void update(@PathVariable("id") int id, @RequestBody OrganizationDomain domain) {
		
		domainService.update(id, domain);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") int id) {
		
		domainService.deleteDomainById(id);
	}
}
