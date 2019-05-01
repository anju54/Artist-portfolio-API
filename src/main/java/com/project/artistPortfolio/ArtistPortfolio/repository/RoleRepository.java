package com.project.artistPortfolio.ArtistPortfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.artistPortfolio.ArtistPortfolio.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Role findByRole(String Role);
}
