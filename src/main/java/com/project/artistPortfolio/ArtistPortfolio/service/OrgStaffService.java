package com.project.artistPortfolio.ArtistPortfolio.service;

import java.util.List;

import com.project.artistPortfolio.ArtistPortfolio.DTO.OrgStaffDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.UpdateUserDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.OrgStaff;

public interface OrgStaffService {

	void addOrgStaff(OrgStaffDTO orgStaffDTO );
	void updateOrgStaff(int id,UpdateUserDTO updateUserDTO);
	OrgStaffDTO getOrgStaffById(int id);
	List<OrgStaffDTO> getallOrgStaff();
	void deleteOrgStaff(int id);
}
