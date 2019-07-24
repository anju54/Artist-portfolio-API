package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.Exhibition;
import com.project.artistPortfolio.ArtistPortfolio.model.Organization;
import com.project.artistPortfolio.ArtistPortfolio.repository.ExhibitionRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.ExhibitionService;
import com.project.artistPortfolio.ArtistPortfolio.service.OrganizationService;

@Service
public class ExhibitionServiceImpl implements ExhibitionService{
	
	private final static Logger logger = LoggerFactory.getLogger(ExhibitionServiceImpl.class);
		
	@Autowired
	private ExhibitionRepository exhibitionRepository;
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * This is used to get Exhibition by id.
	 * 
	 * @param id
	 * 			organization id.
	 * @return Exhibition object.
	 */
	@Override
	public Exhibition getExhibitionByName(String name) {
		
		return null;
	}
	
	/**
	 * This is used to create new organization.
	 * 
	 * @param Exhibition object
	 */
	public void addExhibition(Exhibition exhibition) {
		
		exhibitionRepository.save(exhibition);
	}
	
	/**
	 * This is used to update new organization.
	 * 
	 * @param id
	 * 			organization id.
	 * @param Exhibition object.
	 */
	public void updateExhibition(int id,Exhibition organization) {
		
		
	}
	
	/**
	 * This is used to get Exhibition by id.
	 * 
	 * @param id
	 * 			organization id.
	 * @return Exhibition object.
	 */
	public Exhibition getExhibitionById(int id) {
		
		try {
			
			Optional<Exhibition> exhibition =  exhibitionRepository.findById(id);
			Exhibition exh = exhibition.get();;
			return exh;
		}catch (Exception e) {
			logger.info(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
		 
	}
	
	/**
	 * This is used to get all Exhibition.
	 * 
	 * @return List<Exhibition>.
	 */
	public List<Exhibition> allExhibition(){
		
		 List<Exhibition> allExhibition = exhibitionRepository.findAll();
		 return allExhibition;
	}
	
	/**
	 * This is used to get all Exhibition by org id.
	 * 
	 * @param orgId
	 * 			organization id.
	 * 
	 * @return List<Exhibition>.
	 */
	public List<Exhibition> allExhibitionByOrgId(int orgId){
		
		Organization org = organizationService.getOrganizationById(orgId);
		List<Exhibition> allExhibition = org.getExhibition();
		return allExhibition;
	}
	
	/**
	 * This is used for deleting the organization.
	 */
	public boolean deleteExhibition(int id) {
		
		exhibitionRepository.deleteById(id);
		return true;	
	}

	
}
