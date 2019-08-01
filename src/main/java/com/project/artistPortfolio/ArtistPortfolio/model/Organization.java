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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Model class for organization table.
 * @author anju.kumari
 *
 */
@Entity
@Table(name = "organization")
public class Organization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="organization_id")
	private int organizationId;
	
	@NotNull
	@Column(name = "organization_name",unique = true)
	@Size(max=100)
	private String organizationName;
	 
	@Column(name = "organization_website", unique = true)
	@Size(max=100)
	private String organizationWebsite;
	
	@NotNull
	@Column(name = "address")
	@Size(max=100)
	private String organizationAddress;
	 
	@NotNull
	@Column(name = "contact_number", unique = true)
	@Size(max=14)
	private String contactNumber;
	
	@JsonIgnore
	@OneToMany(mappedBy = "organizationId", cascade = CascadeType.ALL )
	private List<Exhibition> exhibition;
	
//	@OneToMany(mappedBy="organizationId",cascade = CascadeType.ALL)
//	private List<Organizer> organizer;
	
	@JsonIgnore
	@OneToMany(mappedBy="organizationId",cascade = CascadeType.ALL)
	private List<OrgStaff> orgStaff;
	
	@JsonIgnore
	@OneToMany(mappedBy="organization",cascade = CascadeType.ALL)
	private List<OrganizationDomain> domain;
	
	public List<OrganizationDomain> getDomain() {
		return domain;
	}

	public void setDomain(List<OrganizationDomain> domain) {
		this.domain = domain;
	}

	public String getOrganizationAddress() {
		return organizationAddress;
	}

	public void setOrganizationAddress(String organizationAddress) {
		this.organizationAddress = organizationAddress;
	}

	public List<OrgStaff> getOrgStaff() {
		return orgStaff;
	}

	public void setOrgStaff(List<OrgStaff> orgStaff) {
		this.orgStaff = orgStaff;
	}

//	public List<Organizer> getOrganizer() {
//		return organizer;
//	}
//
//	public void setOrganizer(List<Organizer> organizer) {
//		this.organizer = organizer;
//	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationWebsite() {
		return organizationWebsite;
	}

	public void setOrganizationWebsite(String organizationWebsite) {
		this.organizationWebsite = organizationWebsite;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public List<Exhibition> getExhibition() {
		return exhibition;
	}

	public void setExhibition(List<Exhibition> exhibition) {
		this.exhibition = exhibition;
	}
	
}
