package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.DTO.OrganizationDTO;
import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
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
		organization.setOrganizationName(organizationDTO.getOrgName());
		organization.setOrganizationAddress(organizationDTO.getOrgAddress());
		organization.setOrganizationWebsite(organizationDTO.getWebsite());
		organization.setContactNumber(organizationDTO.getContactNo());
		
		organizationRepository.save(organization);
		
		OrganizationDomain domain = new OrganizationDomain();
		domain.setDomainName(organizationDTO.getDomainName());
		
		System.out.println(organizationDTO.getOrgName());
		int organizationID = organizerService.addOrganizer(organizationDTO.getOrgName(), authentication);	
		
		domainService.create(domain,organizationID);
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
		
		existingOrganization.setContactNumber(organization.getContactNumber());
		existingOrganization.setOrganizationName(organization.getOrganizationName());
		existingOrganization.setOrganizationWebsite(organization.getOrganizationWebsite());
		existingOrganization.setOrganizationAddress(organization.getOrganizationAddress());
		organizationRepository.save(existingOrganization);
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
