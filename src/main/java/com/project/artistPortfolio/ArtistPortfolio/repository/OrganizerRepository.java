package com.project.artistPortfolio.ArtistPortfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.artistPortfolio.ArtistPortfolio.model.Organizer;

public interface OrganizerRepository extends JpaRepository<Organizer, Integer> {
	
	public List<Organizer> findAllOrganizerByorganizationId(int organizationId);

}
