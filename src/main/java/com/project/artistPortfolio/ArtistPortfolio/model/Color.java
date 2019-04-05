package com.project.artistPortfolio.ArtistPortfolio.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "colors")
public class Color {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="color_id")
	 private int id;
	 
	 @Column(name="color_name",unique=true)
	 @Size(max=20)
	 private String colorName;
	 
	 @OneToMany(mappedBy="colorId",cascade = CascadeType.ALL)
	 private List<ArtistProfile> artistProfile;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
}
