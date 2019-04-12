package com.project.artistPortfolio.ArtistPortfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.artistPortfolio.ArtistPortfolio.model.Media;

public interface MediaRepository extends JpaRepository<Media, Integer>{
	
	Media getMediaByFileName(String fileName);
	
//
//    @Transactional
//    @Query(value="delete from media where media_id=?1", nativeQuery = true)
//    void deleteMediaById(int id);

}
