package com.project.artistPortfolio.ArtistPortfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.artistPortfolio.ArtistPortfolio.model.OrganizationDomain;

public interface DomainRepository extends JpaRepository<OrganizationDomain, Integer>{
	
	OrganizationDomain getDomainByDomainName(String name);
}
