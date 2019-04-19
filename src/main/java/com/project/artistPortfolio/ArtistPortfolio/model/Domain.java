package com.project.artistPortfolio.ArtistPortfolio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Model class for organizer table 
 * @author anju.kumari
 *
 */
@Entity
@Table(name = "domain")
public class Domain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="domain_id")
	private int domainId;

	@NotNull
	@Column(name = "domain_name",unique = true)
	@Size(max=100)
	private String domainName;
	
	@ManyToOne
	@JoinColumn(name = "org_id", nullable = false)
	private Organization organization;

	public int getDomainId() {
		return domainId;
	}

	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
}
