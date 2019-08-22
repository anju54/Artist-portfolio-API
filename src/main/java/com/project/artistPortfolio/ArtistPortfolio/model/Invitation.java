package com.project.artistPortfolio.ArtistPortfolio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "invitation")
public class Invitation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="invitation_id")
	private int id;
	
	@Column(name = "accepted")
	private boolean accepted;
	
	@Column(name = "rejected")
	private boolean rejected;
	
	@Column(name = "min_slots")
	private int minSlots;
	
	@Column(name = "max_slots")
	private String maxSlots;
	
	@Column(name = "reason_for_rejection")
	private String rejectionReason;
	
	@ManyToOne
	@JoinColumn(name = "exhibition_id", nullable = false)
	private Exhibition exhibitionId;
	
	@OneToOne
	@JoinColumn(name="artist_id")
	private ArtistProfile artistProfile;

	public ArtistProfile getArtistProfile() {
		return artistProfile;
	}

	public void setArtistProfile(ArtistProfile artistProfile) {
		this.artistProfile = artistProfile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public boolean isRejected() {
		return rejected;
	}

	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}

	public int getMinSlots() {
		return minSlots;
	}

	public void setMinSlots(int minSlots) {
		this.minSlots = minSlots;
	}

	public String getMaxSlots() {
		return maxSlots;
	}

	public void setMaxSlots(String maxSlots) {
		this.maxSlots = maxSlots;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public Exhibition getExhibitionId() {
		return exhibitionId;
	}

	public void setExhibitionId(Exhibition exhibitionId) {
		this.exhibitionId = exhibitionId;
	}
	
	

}
