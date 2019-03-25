/**
 * 
 */
package com.project.artistPortfolio.ArtistPortfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.artistPortfolio.ArtistPortfolio.model.LinkTypes;

/**
 * @author anjuk
 *
 */
@Repository
public interface LinksTypesRepository extends JpaRepository<LinkTypes, Integer> {
	
	LinkTypes findByText(String text);
}
