package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.Color;
import com.project.artistPortfolio.ArtistPortfolio.repository.ColorRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.ColorService;

/***
 * This class is used for handling all the operation related to color of the artist profile
 * @author anju.kumari
 *
 */
@Service
public class ColorServiceImpl implements ColorService{
	
	@Autowired
	private ColorRepository colorRepository;
	
	private final static Logger logger = LoggerFactory.getLogger(ColorServiceImpl.class);
	
	/***
	 * This is used to create new color
	 * @param Color object
	 */
	public void create(Color color) {
		
		Color existingColor = getColorByName(color.getColorName());
		
		if(existingColor==null) {
			colorRepository.save(color);
		}else {
			throw  new CustomException(ExceptionMessage.Record_already_exists, HttpStatus.BAD_REQUEST);
		}	
	}
	
	/***
	 * This is used to get color by id
	 * @param id
	 * 		color id
	 * @return Color object
	 */
	public Color getColorById(int id) {
		
		try {
			Color color = colorRepository.findById(id).get();
			return color;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}	
	}
	
	/***
	 * This is used to get list of color
	 */
	public List<Color> getAllColors(){
		
		List<Color> color = colorRepository.findAll();
		return color;	
	}
	
	/***
	 * This is used to delete color by id
	 * @param id
	 * 			color id
	 */
	public void deleteColorById(int id) {
		
		colorRepository.deleteById(id);
	}
	
	/***
	 * This is used to update color by color id
	 * 
	 * @param Color object
	 * @param id
	 * 		color id
	 */
	public void update(int id, Color color) {
		
		Color existingColor = getColorById(id);
		
		existingColor.setColorName(color.getColorName());
		colorRepository.save(existingColor);	
	}
	
	/***
	 * This is used to get color by color name
	 * @param colorname
	 * 		color name
	 * @return Color object
	 */
	public Color getColorByName(String colorname) {
		
		try {
			Color color = colorRepository.getColorByColorName(colorname);
			return color;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
	}

}
