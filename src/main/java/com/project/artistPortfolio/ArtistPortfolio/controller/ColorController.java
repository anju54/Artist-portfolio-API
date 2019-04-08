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

import com.project.artistPortfolio.ArtistPortfolio.model.Color;
import com.project.artistPortfolio.ArtistPortfolio.service.ColorService;

@RestController
@CrossOrigin
@RequestMapping("/api/color")
public class ColorController {
	
	@Autowired
	private ColorService colorService;
	
	@GetMapping("/all")
	public List<Color> getAllColor(){
		
		return colorService.getAllColors();
	}
	
	@GetMapping("/{id}")
	public @ResponseBody Color getColorById(@PathVariable("id") int id) {
		
		return colorService.getColorById(id);
	}
	
	@PostMapping("/")
	public void create(@RequestBody Color color) {
		
		colorService.create(color);
	}
	
	@PutMapping("/{id}")
	public void update(@PathVariable("id") int id, @RequestBody Color color) {
		
		colorService.update(id, color);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") int id) {
		
		colorService.deleteColorById(id);
	}
}
