package com.project.artistPortfolio.ArtistPortfolio.service;

import java.util.List;

import com.project.artistPortfolio.ArtistPortfolio.model.OrganizationDomain;

/**
 * This is used to handle all the operation related to domain
 * @author anjuk
 *
 */
public interface DomainService {
	
	void create(OrganizationDomain domain,int id);
	OrganizationDomain getDomainById(int id);
	List<OrganizationDomain> getAllDomains();
	void deleteDomainById(int id);
	void update(int id, OrganizationDomain domain);
	OrganizationDomain getDomainByName(String name);
	OrganizationDomain getDomainByDomainName(String domainName);
	List<OrganizationDomain> getDomainByOrganizatioId(int id);
	List<OrganizationDomain> getDomainByOrganizerId(int id);

}
