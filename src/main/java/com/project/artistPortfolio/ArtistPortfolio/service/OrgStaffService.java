package com.project.artistPortfolio.ArtistPortfolio.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import com.project.artistPortfolio.ArtistPortfolio.DTO.OrgStaffDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.Response;
import com.project.artistPortfolio.ArtistPortfolio.DTO.UpdateUserDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.Exhibition;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.OrgStaff;
import com.project.artistPortfolio.ArtistPortfolio.model.Organization;

public interface OrgStaffService {

	void addOrgStaff(OrgStaffDTO orgStaffDTO );
	boolean updateOrgStaff(int id,UpdateUserDTO updateUserDTO);
	OrgStaffDTO getOrgStaffById(int id);
	List<OrgStaffDTO> getallOrgStaff();
	void deleteOrgStaff(int id);
	List<OrgStaffDTO> getStaffListByOrganizationId(int id);
	Media getProfilePicByUserId(int id);
	ResponseEntity<?> uploadprofilePicForOrgStaff(Authentication authentication,MultipartFile file)throws IOException;
	int addOrgStaffAsAdmin(String organizationName, String email);
	Organization getOrganizationByOrgStaffId(int id);
	Organization getOrganizationByUserId(int id);
	OrgStaff getStaffByStaffId(int id);
	Response<OrgStaff> assignStaffForExhibition(String exhibitionTitle,int staffId);
	Exhibition getExhibitionByOrgStaff(int id);
	int getOrgStaffIdByUserId(int userId);
}
