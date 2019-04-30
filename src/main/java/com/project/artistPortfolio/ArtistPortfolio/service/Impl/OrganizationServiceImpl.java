package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.artistPortfolio.ArtistPortfolio.DTO.OrganizationDTO;
import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.exception.OrgContactExists;
import com.project.artistPortfolio.ArtistPortfolio.exception.OrgNameExists;
import com.project.artistPortfolio.ArtistPortfolio.exception.OrgWebsiteExists;
import com.project.artistPortfolio.ArtistPortfolio.model.Organization;
import com.project.artistPortfolio.ArtistPortfolio.model.OrganizationDomain;
import com.project.artistPortfolio.ArtistPortfolio.repository.OrganizationRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.DomainService;
import com.project.artistPortfolio.ArtistPortfolio.service.OrganizationService;
import com.project.artistPortfolio.ArtistPortfolio.service.OrganizerService;

@Service
public class OrganizationServiceImpl implements OrganizationService{
	
	private final static Logger logger = LoggerFactory.getLogger(LinksServiceImpl.class);
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private OrganizerService organizerService;
	
	@Autowired
	private DomainService domainService;
	
	/**
	 * This is used to get Organization by id.
	 * 
	 * @param id
	 * 			organization id.
	 * @return Organization object.
	 */
	@Override
	public Organization getOrganizationByName(String name) {
		
		return organizationRepository.findByOrganizationName(name);
	}
	
	/**
	 * This is used to create new organization.
	 * 
	 * @param Organization object
	 */
	public void addOrganization(OrganizationDTO organizationDTO,Authentication authentication) {
		
		Organization organization = new Organization();
		try {
			organization.setOrganizationName(organizationDTO.getOrgName());
			Organization org = organizationRepository.findByOrganizationName(organizationDTO.getOrgName());
			if(org!=null) {
				throw new OrgNameExists("org name is already taken");
			}
			organization.setOrganizationAddress(organizationDTO.getOrgAddress());
			
			organization.setOrganizationWebsite(organizationDTO.getWebsite());
			Organization orgWebsite = organizationRepository.findByOrganizationWebsite(organizationDTO.getWebsite());
			if(orgWebsite!=null) {
				throw new OrgWebsiteExists("org website is already taken");
			}
			
			organization.setContactNumber(organizationDTO.getContactNo());
			Organization orgContactNo = organizationRepository.findByContactNumber(organizationDTO.getContactNo());
			if(orgContactNo!=null) {
				throw new OrgContactExists("org contact is already taken");
			}
			
			organizationRepository.save(organization);
			
			OrganizationDomain domain = new OrganizationDomain();
			domain.setDomainName(organizationDTO.getDomainName());
			
			int organizationID = organizerService.addOrganizer(organizationDTO.getOrgName(), authentication);	
			
			domainService.create(domain,organizationID);
			
		}catch (OrgNameExists e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Organization is already registered with us!!");
		}catch (OrgWebsiteExists e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This website is already registered with us!!");
		}catch (OrgContactExists e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This contact number has already taken!!");
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Opps!! error occured while registration.");
		}
		
	}
	
	/**
	 * This is used to update new organization.
	 * 
	 * @param id
	 * 			organization id.
	 * @param Organization object.
	 */
	public void updateOrganization(int id,Organization organization) {
		
		Organization existingOrganization = getOrganizationById(id);
		try {
			existingOrganization.setContactNumber(organization.getContactNumber());
			Organization orgContactNo = organizationRepository.findByContactNumber(organization.getContactNumber());
			if(orgContactNo!=null && id!=orgContactNo.getOrganizationId()) {
				throw new OrgContactExists("org contact is already taken");
			}
			
			existingOrganization.setOrganizationName(organization.getOrganizationName());
			Organization org = organizationRepository.findByOrganizationName(organization.getOrganizationName());
			if(org!=null && id!=org.getOrganizationId()) {
				throw new OrgNameExists("org name is already taken");
			}
			
			existingOrganization.setOrganizationWebsite(organization.getOrganizationWebsite());
			Organization orgWebsite = organizationRepository.findByOrganizationWebsite(organization.getOrganizationWebsite());
			if(orgWebsite!=null && id!=orgWebsite.getOrganizationId()) {
				throw new OrgWebsiteExists("org website is already taken");
			}
			existingOrganization.setOrganizationAddress(organization.getOrganizationAddress());
			organizationRepository.save(existingOrganization);
			
		}catch (OrgNameExists e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This Organization is already registered with us!!");
		}catch (OrgWebsiteExists e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This website is already registered with us!!");
		}catch (OrgContactExists e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This contact number has already taken!!");
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Opps!! error occured while registration.");
		}
	}
	
	/**
	 * This is used to get Organization by id.
	 * 
	 * @param id
	 * 			organization id.
	 * @return Organization object.
	 */
	public Organization getOrganizationById(int id) {
		
		try {
			Organization existingOrganization = organizationRepository.findById(id).get();
			return existingOrganization;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * This is used to get Organization by id.
	 * 
	 * @return List<Organization>.
	 */
	public List<Organization> allOrganization(){
		
		return organizationRepository.findAll();
	}
	
	/**
	 * This is used for deleting the organization.
	 */
	public void deleteOrganization(int id) {
		
		organizationRepository.deleteById(id);
	}

	
}
