package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.artistPortfolio.ArtistPortfolio.DTO.Response;
import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.DuplicateRecord;
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
	public Exhibition getExhibitionByTitle(String title) {
		
		try {
			Exhibition exhibition =exhibitionRepository.findByTitle(title);
			
			return  exhibition;
			
		}catch (Exception e) {
			logger.info(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
	
	}
	
	/**
	 * This is used to create new organization.
	 * 
	 * @param title 
	 * 		name or title of the organization
	 * @return Response object
	 */
	public Response<Exhibition> addExhibition(String title,String organization) {
		
		Exhibition exhibition = new Exhibition();
		Response<Exhibition> returnObject = new Response<>();
		
		try {
			Organization org =  organizationService.getOrganizationByName(organization);
			
			Exhibition exh = getExhibitionByTitle(title);
			if(exh!=null) {
				logger.info("duplicate exhibition title");
				throw new DuplicateRecord("duplicate ecord");
			}
			
			exhibition.setTitle(title);
			exhibition.setOrganizationId(org.getOrganizationId());
			
			returnObject.setResponse(exhibition);
			
			exhibitionRepository.save(exhibition);
			returnObject.setStatus("success");
			return returnObject;
			
		}catch (DuplicateRecord e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Exhibition is alredy registered with us!!");
		}catch (Exception e) {
			
			returnObject.setStatus("failed");
			return returnObject;	
		}
		
	}
	
	/**
	 * This is used to update new exhibition.
	 * 
	 * @param id
	 * 			exhibition id.
	 * @param Exhibition object.
	 * 
	 * @return Response object
	 */
	public Response<Exhibition> updateExhibition(int id,Exhibition exhibition) {
		
		Response<Exhibition> response = new Response<>();
		
		Exhibition existingExhibition = getExhibitionById(id);
		
		existingExhibition.setDate(exhibition.getDate());
		existingExhibition.setPaintingSlots(exhibition.getPaintingSlots());
		existingExhibition.setVenue(exhibition.getVenue());
	
		exhibitionRepository.save(existingExhibition);
		logger.info("data updated");
		
		response.setResponse(exhibition);
		response.setStatus("success");
		return response;
	}
	
	/**
	 * This is used to get Exhibition by id.
	 * 
	 * @param id
	 * 			Exhibition id.
	 * @return Exhibition object.
	 */
	public Exhibition getExhibitionById(int id) {
		
		try {
			
			Optional<Exhibition> exhibition =  exhibitionRepository.findById(id);
			Exhibition exh = exhibition.get();
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
	@Override
	public List<Exhibition> allExhibitionByOrgId(int orgId){
		
		Organization org = organizationService.getOrganizationById(orgId);
		List<Exhibition> allExhibition = org.getExhibition();
		return allExhibition;
	}
		
	 /**
	  * This is used to delete exhibition by id
	  * @param id
	  * 	exhibition id
	  * @return true or false.
	 */
	@Override
	public boolean deleteExhibition(int id) {
		try {
			exhibitionRepository.deleteById(id);
			return true;
		}catch (Exception e) {
			return false;}
	}

	
}
