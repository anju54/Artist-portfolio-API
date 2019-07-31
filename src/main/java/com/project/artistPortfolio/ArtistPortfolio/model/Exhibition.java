package com.project.artistPortfolio.ArtistPortfolio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "exhibition")
public class Exhibition {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="exhibition_id")
	private int id;
	
	@Column(name = "title")
	@Size(max=200)
	private String title;
	
	@Column(name = "painting_slots")
	private int paintingSlots;
	
	@Column(name = "venue")
	@Size(max=100)
	private String venue;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "organization_id")
	private int organizationId;
	
	@OneToMany( mappedBy="media")
	private List<ExhibitionMedia> exhibitionMedia;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable( name = "exhibition_orgstaff_bridge", joinColumns = @JoinColumn( name = "exhibition_id" ),
				inverseJoinColumns = @JoinColumn( name = "org_staff_id")  )
	List<OrgStaff> orgStaffs;

	public List<OrgStaff> getOrgStaffs() {
		return orgStaffs;
	}

	public List<ExhibitionMedia> getExhibitionMedia() {
		return exhibitionMedia;
	}

	public void setExhibitionMedia(List<ExhibitionMedia> exhibitionMedia) {
		this.exhibitionMedia = exhibitionMedia;
	}

	public void setOrgStaffs(List<OrgStaff> orgStaffs) {
		this.orgStaffs = orgStaffs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPaintingSlots() {
		return paintingSlots;
	}

	public void setPaintingSlots(int paintingSlots) {
		this.paintingSlots = paintingSlots;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
}
