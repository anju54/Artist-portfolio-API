package com.project.artistPortfolio.ArtistPortfolio.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMedia;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMediaKey;

@Repository
public interface ArtistProfileMediaRepository extends PagingAndSortingRepository<ArtistProfileMedia, ArtistProfileMediaKey> {
	
	List<ArtistProfileMedia> findArtistProfileMediaByArtistProfileId(int id,Pageable pageable); // id is artistProfileId 
	
}
