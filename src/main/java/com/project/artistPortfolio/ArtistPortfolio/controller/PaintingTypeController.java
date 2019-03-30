package com.project.artistPortfolio.ArtistPortfolio.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.project.artistPortfolio.ArtistPortfolio.model.PaintingType;
import com.project.artistPortfolio.ArtistPortfolio.service.PaintingTypeService;
import com.project.artistPortfolio.ArtistPortfolio.service.Impl.LinksServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("/api/painting-type")
public class PaintingTypeController {
	
	private final static Logger logger = LoggerFactory.getLogger(LinksServiceImpl.class);
	
	@Autowired
	private PaintingTypeService paintingTypeService;
	
	@GetMapping("/{id}")
	public @ResponseBody PaintingType getPaintingTypeById(@PathVariable("id") int id) {
		
		logger.info("trying to get painting type");
		return paintingTypeService.getPaintingById(id);	
	}
	
	@PostMapping("/new")
	public void create(@RequestBody PaintingType paintingType) {
		paintingTypeService.createPainting(paintingType);
	}
	
	@PutMapping("/{id}")
	public void update(@PathVariable("id") int id, @RequestBody PaintingType paintingType) {
		
		paintingTypeService.updatePainting(paintingType, id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") int id) {
		
		paintingTypeService.deletePaintingById(id);
	}
	
	/**
	 * This is used to display all the painting type
	 * @return List<PaintingType>
	 */
	@GetMapping("/all")
	public List<PaintingType> getAllpainting() {
		
		return paintingTypeService.getAllPaintingType();
	}

}
