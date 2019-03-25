package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.LinkTypes;
import com.project.artistPortfolio.ArtistPortfolio.repository.LinksTypesRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.LinkTypesService;

@Service
public class LinkTypesServiceImpl implements LinkTypesService{
	
	private final static Logger logger = LoggerFactory.getLogger(LinkTypesServiceImpl.class);
	
	@Autowired
	private LinksTypesRepository linksTypesRepository;
	
	/***
	 * This is used for creating the link to reference id for token generation
	 */
	@Override
	public LinkTypes createLinkTypes(LinkTypes linkTypes) {
		
		try {
			LinkTypes existingLinkType = linksTypesRepository.findByText(linkTypes.getText());
			if (existingLinkType==null) {
				linksTypesRepository.save(linkTypes);
				return linkTypes;
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
		return null;
	}
	
	public LinkTypes getLinksTypesById(int id) {
		
		try {
			Optional<LinkTypes> linkTypesOp = linksTypesRepository.findById(id);
			LinkTypes linkTypes = linkTypesOp.get();
			return linkTypes;	
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "record already exists");
		}	
	}

}
