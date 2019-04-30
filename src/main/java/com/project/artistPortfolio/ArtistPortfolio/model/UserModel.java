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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



/**
 * Model class for users table 
 * @author anjuk
 *
 */
@Entity
@Table(name = "users")
public class UserModel {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="user_id")
	 private int id;
	 
	 @NotNull
	 @Column(name = "fname")
	 @Size(max=20)
	 private String fname;
	 
	 @Column(name = "lname")
	 @Size(max=20)
	 private String lname;
	 
	 @NotNull
	 @Column(name = "email", unique = true)
	 @Size(max=40)
	 private String email;
	 
	 @Column(name = "password")
	 private String password;
	 
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	 
	@OneToOne(mappedBy="user")
	private ArtistProfile artistProfile;
	
	@OneToOne(mappedBy="user")
	private OrgStaff orgStaf;
	
	@OneToOne(mappedBy="user")
	private Organizer organizer;
	 
	public UserModel() {
		super();
	}

	public UserModel(@NotNull @Size(max = 20) String fname, @Size(max = 20) String lname,
			@NotNull @Size(max = 40) String email, String password) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.password = password;
		//this.role = role;
	}
	
	public OrgStaff getOrgStaf() {
		return orgStaf;
	}

	public void setOrgStaf(OrgStaff orgStaf) {
		this.orgStaf = orgStaf;
	}

	public Organizer getOrganizer() {
		return organizer;
	}

	public void setOrganizer(Organizer organizer) {
		this.organizer = organizer;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

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

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	 
//	 @ManyToOne
//	 @JoinColumn(name = "role_id", nullable = false)
//	 private Role role;
	 
	 

}
