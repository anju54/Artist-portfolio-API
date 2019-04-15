package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.LinkTypes;
import com.project.artistPortfolio.ArtistPortfolio.model.Links;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.LinksRepository;
import com.project.artistPortfolio.ArtistPortfolio.repository.LinksTypesRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.LinkTypesService;
import com.project.artistPortfolio.ArtistPortfolio.service.LinksService;
import com.project.artistPortfolio.ArtistPortfolio.service.MediaService;
import com.project.artistPortfolio.ArtistPortfolio.service.UserService;

/**
 * This is used to handle all the operation related  to LinkService.
 * @author anjuk
 *
 */
@Service
public class LinksServiceImpl implements LinksService {
	
	@Autowired
	private LinksTypesRepository linksTypesRepository;
	
	@Autowired
	private LinksRepository linksRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LinkTypesService linktypesService;
	
	@Autowired
	private MediaService mediaService;
	
	private final static Logger logger = LoggerFactory.getLogger(LinksServiceImpl.class);
	
	/***
	 * This method helps to create token for any request
	 */
	public void createLinks(String text, String email, int id,String token) { // id can be userID and mediaId
		
		try {
			Links links = new Links();
			
			//String token = UUID.randomUUID().toString();
			
			Date date = new Date(System.currentTimeMillis() + 60*60 * 1000); // 1hr
			
			LinkTypes linkTypes = linksTypesRepository.findByText(text);
			logger.info(linkTypes.getText());
			
			links.setToken(token);
			links.setExpiryDate(date);
			
			links.setLinkTypes(linkTypes);
			linksRepository.save(links);
			
			String field = linktypesService.getLinksTypesById(links.getLinkTypes().getId()).getField();
			if(field.equalsIgnoreCase("user_id")) {
				
				UserModel user = userService.getUserById(id);
				logger.info(user.getEmail());
				links.setRefrenceId(user.getId());
				linksRepository.save(links);
				
			}else if(field.equalsIgnoreCase("media_id")) {
				
				Media media = mediaService.getMediaById(id);
				links.setRefrenceId(media.getId());
				linksRepository.save(links);	
			} else {
				logger.info("no condition matched for setting the value of refrenceId while creating token");
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.BAD_REQUEST);
		}	
	}
		
	public Links getLinksById(int id) {
		
		try {
			Links links = linksRepository.findById(id).get();
			return links;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.BAD_REQUEST);
			// TODO: handle exception
		}
	}

}
