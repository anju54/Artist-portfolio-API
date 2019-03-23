package com.project.artistPortfolio.ArtistPortfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
	
	UserModel findByEmail(String email);

}
