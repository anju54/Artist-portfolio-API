package com.project.artistPortfolio.ArtistPortfolio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	
	@OneToOne(mappedBy="exhibition")
	private OrgStaff orgStaff;
	
	public OrgStaff getOrgStaff() {
		return orgStaff;
	}

	public void setOrgStaff(OrgStaff orgStaff) {
		this.orgStaff = orgStaff;
	}

	public List<ExhibitionMedia> getExhibitionMedia() {
		return exhibitionMedia;
	}

	public void setExhibitionMedia(List<ExhibitionMedia> exhibitionMedia) {
		this.exhibitionMedia = exhibitionMedia;
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
