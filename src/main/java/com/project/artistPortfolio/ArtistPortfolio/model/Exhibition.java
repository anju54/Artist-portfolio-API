package com.project.artistPortfolio.ArtistPortfolio.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "exhibition",
			uniqueConstraints=
			@UniqueConstraint(columnNames= {"venue","date"})
)
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
	
	@JsonIgnore
	@OneToMany( mappedBy="media")
	private List<ExhibitionMedia> exhibitionMedia;
	
	@JsonIgnore
	@OneToMany(mappedBy="exhibition_id")
	private List<OrgStaff> orgStaff;
	
	public List<OrgStaff> getOrgStaff() {
		return orgStaff;
	}

	public void setOrgStaff(List<OrgStaff> orgStaff) {
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
