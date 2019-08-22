package com.project.artistPortfolio.ArtistPortfolio.DTO;

public class InvitationDTO {

	private boolean accepted;
	private boolean rejected;
	private int minSlots;
	private String maxSlots;
	private String rejectionReason;
	private int exhibitionId;
	private int artist_id;
	
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
	public int getExhibitionId() {
		return exhibitionId;
	}
	public void setExhibitionId(int exhibitionId) {
		this.exhibitionId = exhibitionId;
	}
	public int getArtist_id() {
		return artist_id;
	}
	public void setArtist_id(int artist_id) {
		this.artist_id = artist_id;
	}
	
	
}
