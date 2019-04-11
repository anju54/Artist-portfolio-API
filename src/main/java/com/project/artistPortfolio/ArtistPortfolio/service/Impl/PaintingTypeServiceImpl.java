package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.PaintingType;
import com.project.artistPortfolio.ArtistPortfolio.repository.PaintingTypeRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.PaintingTypeService;
/***
 * this is used to handle all the operation related to painting type
 * @author anju.kumari
 *
 */
@Service
public class PaintingTypeServiceImpl implements PaintingTypeService{
	
	@Autowired
	private PaintingTypeRepository paintingTypeRepository;
	
	private final static Logger logger = LoggerFactory.getLogger(PaintingTypeServiceImpl.class);
	
	/***
	 * This is used to get the painting type by id
	 * 
	 * @param id
	 * 		painting type id
	 * 
	 * @return paintingType object
	 */
	public PaintingType getPaintingById(int id) {
		
		try {
			PaintingType paintingType = paintingTypeRepository.findById(id).get();
			return paintingType;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
	}
	
	/***
	 * This is used to create new paintingType
	 * 
	 * @param  PaintingType
	 */
	public void createPainting(PaintingType paintingType) {
		
		paintingTypeRepository.save(paintingType);
	}
	
	/***
	 * This is used to update paintingType
	 * 
	 * @param PaintingType
	 * @param id
	 * 			Painting Type id
	 */
	public void updatePainting(PaintingType paintingType, int id) {
		
		PaintingType existingPainting = getPaintingById(id);
		
		if (existingPainting!=null) {
			
			existingPainting.setPaintingName(paintingType.getPaintingName());
			paintingTypeRepository.save(existingPainting);
		}
	}
	
	/***
	 * This is used to get all painting type
	 * 
	 * @return list of PaintingType
	 */
	public List<PaintingType> getAllPaintingType(){
		
		List<PaintingType> paintingType = paintingTypeRepository.findAll();
		return paintingType;	
	}
	
	/**
	 * This is used to get PaintingType by paintingName
	 * @param paintingName
	 * @return PaintingType object
	 */
	public PaintingType getPaintingTypeByPaintingName(String paintingName) {
		
		try {
			return paintingTypeRepository.findPaintingTypeByPaintingName(paintingName);
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
		 
	}
	
	/***
	 * this is used to delete painting type by id
	 * 
	 * @param id
	 * 		painting type id
	 */
	public void deletePaintingById(int id) {
		
		paintingTypeRepository.deleteById(id);
	}

}
