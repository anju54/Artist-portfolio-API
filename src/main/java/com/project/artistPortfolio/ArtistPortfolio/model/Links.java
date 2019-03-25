package com.project.artistPortfolio.ArtistPortfolio.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "links")
public class Links {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="link_id")
	 private int id;
	 
	 @Column(name="token",unique=true)
	 private String token;
	 
	 //private static final int EXPIRATION = 60 * 24;
	 
	 @Column(name="expiry_date")
	 private Date expiryDate;
	 
	 @Column(name="refrence_id")
	 private int refrenceId;
	 
	 @ManyToOne
	 @JoinColumn(name = "link_type_id", nullable = false)
	 private LinkTypes linkTypes;

	public LinkTypes getLinkTypes() {
		return linkTypes;
	}

	public void setLinkTypes(LinkTypes linkTypes) {
		this.linkTypes = linkTypes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getRefrenceId() {
		return refrenceId;
	}

	public void setRefrenceId(int refrenceId) {
		this.refrenceId = refrenceId;
	}
}
