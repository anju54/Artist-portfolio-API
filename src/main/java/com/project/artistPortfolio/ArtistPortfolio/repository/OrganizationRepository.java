package com.project.artistPortfolio.ArtistPortfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.artistPortfolio.ArtistPortfolio.model.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
	
	Organization findByOrganizationName(String organizationName);
	Organization findByOrganizationWebsite(String organizationWebsite);
	Organization findByContactNumber(String organizationContactNumber);

}
