package com.project.artistPortfolio.ArtistPortfolio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.artistPortfolio.ArtistPortfolio.model.LinkTypes;
import com.project.artistPortfolio.ArtistPortfolio.service.LinkTypesService;

@RestController
@CrossOrigin
@RequestMapping("/api/link_types")
public class LinkTypesController {
	
	@Autowired
	private LinkTypesService linktypesService;
	
	private final static Logger logger = LoggerFactory.getLogger(LinkTypesController.class);
	
	@PostMapping("/links_types")
	public void createLinkTypes(@RequestBody LinkTypes linkTypes) {
		
		logger.info("trying to create linktypes");
		linktypesService.createLinkTypes(linkTypes);	
	}
	
	@GetMapping("/{id}")
	public @ResponseBody LinkTypes getLinkTypeById(@PathVariable("id") int id) {
		
		return linktypesService.getLinksTypesById(id);
	}

}
