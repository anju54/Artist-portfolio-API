package com.project.artistPortfolio.ArtistPortfolio.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "links")
public class Links {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="link_id")
	 private int id;
	 
	 @Column(name="token")
	 private String token;
	 
	 @Column(name="expiry_date")
	 private Timestamp expiryDate;
	 
	 @Column(name="refrence_id")
	 private int refrenceId;

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

	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getRefrenceId() {
		return refrenceId;
	}

	public void setRefrenceId(int refrenceId) {
		this.refrenceId = refrenceId;
	}
	 
	 

}
