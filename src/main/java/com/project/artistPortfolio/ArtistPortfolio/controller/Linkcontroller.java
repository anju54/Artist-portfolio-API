package com.project.artistPortfolio.ArtistPortfolio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.artistPortfolio.ArtistPortfolio.model.Links;
import com.project.artistPortfolio.ArtistPortfolio.service.LinksService;
import com.project.artistPortfolio.ArtistPortfolio.service.Impl.LinksServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("/api/links")
public class Linkcontroller {
	
	@Autowired
	private LinksService linksService;
	
	private final static Logger logger = LoggerFactory.getLogger(LinksServiceImpl.class);
		
	@GetMapping("/{id}")
	public @ResponseBody Links getLinkById(@PathVariable("id") int id) {
		
		logger.info("trying to get links");
		return linksService.getLinksById(id);
	}

}
