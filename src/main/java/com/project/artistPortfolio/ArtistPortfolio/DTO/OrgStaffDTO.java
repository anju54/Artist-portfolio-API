package com.project.artistPortfolio.ArtistPortfolio.DTO;

public class OrgStaffDTO {
	
	private String email;
	private String fName;
	private String lName;
	private String organizationName;
	private String roleName;
	private int orgStaffId;
	
	public int getOrgStaffId() {
		return orgStaffId;
	}
	public void setOrgStaffId(int orgStaffId) {
		this.orgStaffId = orgStaffId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
}
