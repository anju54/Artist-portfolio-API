package com.project.artistPortfolio.ArtistPortfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.artistPortfolio.ArtistPortfolio.model.Color;

public interface ColorRepository extends JpaRepository<Color, Integer>{

	Color getColorByColorName(String ColorName);
}
