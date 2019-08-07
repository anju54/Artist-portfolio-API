package com.project.artistPortfolio.ArtistPortfolio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Model class for organization table.
 * @author anju.kumari
 *
 */
@Entity
@Table(name = "org_staff")
public class OrgStaff {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="org_staff_id")
	private int id;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="user_id",unique=true)
	private UserModel user;
	
	@Column(name="organization_id")
	private int organizationId;
	
	@Column(name="exhibition_id")
	private int exhibition_id;
	
	@OneToOne
	@JoinColumn(name="profile_pic_id")
	private Media media;
	
	public int getExhibition_id() {
		return exhibition_id;
	}

	public void setExhibition_id(int exhibition_id) {
		this.exhibition_id = exhibition_id;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	
	
	
}
