package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.DomainNameExists;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.Organization;
import com.project.artistPortfolio.ArtistPortfolio.model.OrganizationDomain;
import com.project.artistPortfolio.ArtistPortfolio.repository.DomainRepository;
import com.project.artistPortfolio.ArtistPortfolio.repository.OrganizationRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.DomainService;

/***
 * This class is used for handling all the operation related to domain of the artist profile
 * @author anju.kumari
 *
 */
@Service
public class DomainServiceImpl implements DomainService{
	
	@Autowired
	private DomainRepository domainRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	private final static Logger logger = LoggerFactory.getLogger(DomainServiceImpl.class);
	
	/***
	 * This is used to create new domain
	 * @param Domain object
	 * 
	 * @param id
	 * 			organization id
	 */
	public void create(OrganizationDomain domain,int id) {
		
		try {
			logger.info("Trying to create domain");
			System.out.println("organizatio id "+id);
			Organization org =  organizationRepository.findById(id).get();
			
			if(org==null) {
				throw new DomainNameExists( "organization not found");
			}
			
			domain.setOrganization(org);
			domainRepository.save(domain);	
			logger.info("domain has been created!!");
			
		}catch (DomainNameExists e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Organization not found");
		}catch (Exception e) {
			logger.info(e.getMessage());
		}
		
	}
	
	/***
	 * This is used to get domain by id
	 * @param id
	 * 		domain id
	 * @return Domain object
	 */
	public OrganizationDomain getDomainById(int id) {
		
		try {
			OrganizationDomain domain = domainRepository.findById(id).get();
			return domain;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}	
	}
	
	/***
	 * This is used to get domain by name
	 * 
	 * @param domainName
	 * 	
	 * @return Domain object
	 */
	public OrganizationDomain getDomainByDomainName(String domainName) {
		
		try {
			OrganizationDomain domain = domainRepository.getDomainByDomainName(domainName);
			return domain;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
	}
	
	/***
	 * This is used to get list of domain
	 */
	public List<OrganizationDomain> getAllDomains(){
		
		List<OrganizationDomain> domain = domainRepository.findAll();
		return domain;	
	}
	
	/***
	 * This is used to delete domain by id
	 * @param id
	 * 			domain id
	 */
	public void deleteDomainById(int id) {
		
		domainRepository.deleteById(id);
	}
	
	/***
	 * This is used to update domain by domain id
	 * 
	 * @param Domain object
	 * @param id
	 * 		domain id
	 */
	public void update(int id, OrganizationDomain domain) {
		
		OrganizationDomain existingDomain = getDomainById(id);
		
		existingDomain.setDomainName(domain.getDomainName());
		domainRepository.save(existingDomain);	
	}
	
	/***
	 * This is used to get domain by domain name
	 * @param domainname
	 * 		domain name
	 * @return Domain object
	 */
	public OrganizationDomain getDomainByName(String domainname) {
		
		try {
			OrganizationDomain domain = domainRepository.getDomainByDomainName(domainname);
			return domain;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This is used to get domain by organization id
	 * @param id
	 * 			organization id
	 * @return List of OrganizationDomain 
	 */
	public List<OrganizationDomain> getDomainByOrganizatioId(int id) {
		
		Organization org = organizationRepository.findById(id).get();
		return org.getDomain();
	}
	
	/**
	 * This is used to get domain by organization id
	 * @param id
	 * 			organizer id
	 * @return List of OrganizationDomain 
	 */
	public List<OrganizationDomain> getDomainByOrganizerId(int id) {
		
		//Organizer organizer = organizerService.getOrganizerById(id);
//		Organization org = organizationRepository.findById(organizer.getOrganizationId()).get();
//		return org.getDomain();
		return null;
	}
	

}
