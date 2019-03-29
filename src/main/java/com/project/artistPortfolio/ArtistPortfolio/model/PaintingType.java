package com.project.artistPortfolio.ArtistPortfolio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "painting_type")
public class PaintingType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="painting_type_id")
	private int id;
	
	@NotNull
	@Column(name = "painting_name",unique=true)
	@Size(max=40)
	private String paintingName;
	
	@JsonIgnore
	@ManyToMany(mappedBy="paintingType",fetch = FetchType.LAZY)
	List<ArtistProfile> artistPainting;
	
	public List<ArtistProfile> getArtistPainting() {
		return artistPainting;
	}

	public void setArtistPainting(List<ArtistProfile> artistPainting) {
		this.artistPainting = artistPainting;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPaintingName() {
		return paintingName;
	}

	public void setPaintingName(String paintingName) {
		this.paintingName = paintingName;
	}
}
