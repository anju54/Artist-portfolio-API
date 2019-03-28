package com.project.artistPortfolio.ArtistPortfolio.controller;

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

import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileService;

@RestController
@CrossOrigin
@RequestMapping("/api/artist-profile")
public class ArtistProfileController {
	
	@Autowired
	ArtistProfileService artistProfileService;
	
	@GetMapping("/{id}")
	public void getArtistProfileByid(int id) {
		
		artistProfileService.getArtistProfileById(id);
	}
	
	@DeleteMapping("/{id}")
	public void deleteArtistProfile(int id) {
		
		artistProfileService.deleteByid(id);
	}
	
	@PostMapping("/")
	public void create(@RequestBody ArtistProfile artistProfile) {
		
		artistProfileService.createArtistProfileRecord(artistProfile);
	}

	@PutMapping("/{id}")
	public void update(@RequestBody ArtistProfile artistProfile, @PathVariable("id") int id) {
		
		artistProfileService.updateArtistProfileRecord(artistProfile, id);
	}
}
