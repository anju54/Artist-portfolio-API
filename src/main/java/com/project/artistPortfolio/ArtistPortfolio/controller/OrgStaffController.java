package com.project.artistPortfolio.ArtistPortfolio.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.artistPortfolio.ArtistPortfolio.DTO.OrgStaffDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.UpdateUserDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.Exhibition;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.Organization;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.service.OrgStaffService;
import com.project.artistPortfolio.ArtistPortfolio.service.UserService;

/**
 * This is used for mapping all the operation related to orgStaff
 * @author anjuk
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/api/orgStaff")
public class OrgStaffController {
	
	@Autowired
	private OrgStaffService orgStaffService;
	
	@Autowired
	private UserService userService;

	/***
	 * This is used to get orgStaff id
	 * 
	 * @param userId
	 * 
	 * @return id
	 * 			orgStaff id
	 */
	@GetMapping("/user/{id}")
	public int getOrgStaffIdByUserId(@PathVariable("id") int id) {
		
		return orgStaffService.getOrgStaffIdByUserId(id);
	}
	
	/**
	 * This is used to get artist profile pic path by profile id
	 * @param id
	 * @return Media object
	 */
	@GetMapping("/profile-pic")
	public Media getProfilePicByArtistProfileId(Authentication authentication) {
		
		String email = userService.getPrincipalUser(authentication).getUsername();
		UserModel user = userService.getUserByEmail(email);
		int id = user.getId();   // user id
		
		return orgStaffService.getProfilePicByUserId(id);
	}
	
	/**
	 * This is used to update profile pic of particular user
	 * @param email
	 * @param file
	 * @return 
	 * @throws IOException
	 */
	@PostMapping(value="/upload/profile-pic", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadFile(MultipartFile file,Authentication authentication) throws IOException{
		
		return orgStaffService.uploadprofilePicForOrgStaff(authentication, file);
	}
	
	/**
	 * This is used to create new orgStaff.
	 * 
	 * @param orgStaff object
	 */
	@PostMapping("/")
	public void createNeworgStaff(@RequestBody OrgStaffDTO orgStaffDTO) {
		
		System.out.println("2..............................");
		orgStaffService.addOrgStaff(orgStaffDTO);
	}
	
	/**
	 * This is used to update new orgStaff.
	 * 
	 * @param id
	 * 			orgStaff id.
	 * @param orgStaff object.
	 */
	@PutMapping("/{id}")
	public boolean updateorgStaff(@PathVariable("id") int id, @RequestBody UpdateUserDTO updateUserDTO) {
		return orgStaffService.updateOrgStaff(id, updateUserDTO);
	}
	
	/**
	 * This is used to get orgStaff by id.
	 * 
	 * @param id
	 * 			orgStaff id.
	 * @return orgStaff object.
	 */
	@GetMapping("/{id}")
	public OrgStaffDTO getorgStaffByid(@PathVariable("id") int id) {
		
		return orgStaffService.getOrgStaffById(id);
	}
	
	/**
	 * This is used to get organization detail by org staff(admin or staff) id
	 * @param id
	 * 			org staff id
	 * @return Organization object;
	 */
	@GetMapping("/{id}/organization")
	public Organization getOrganization(@PathVariable("id") int id) {
		
		return orgStaffService.getOrganizationByOrgStaffId(id);
	}
	
	/**
	 * This is used to get organization detail by user id
	 * @param id
	 * 			user id
	 * @return Organization object;
	 */
	@GetMapping("/user/{id}/organization")
	public Organization getOrganizationByUserId(@PathVariable("id") int id) {
		
		return orgStaffService.getOrganizationByUserId(id);
	}
	
	
	
	/**
	 * This is used to get all orgStaff .
	 * 
	 * @return List<orgStaff>.
	 */
	@GetMapping("/all")
	public List<OrgStaffDTO> getAllorgStaff(){
		return orgStaffService.getallOrgStaff();
	}
	
	/**
	 * This is used to get orgStaff by organization id.
	 * 
	 * @param id
	 * 			organization id
	 * @return List<orgStaffDTO>.
	 */
	@GetMapping("/all/organization/{id}")
	public List<OrgStaffDTO> getAllorgStaffById(@PathVariable("id") int id){
		return orgStaffService.getStaffListByOrganizationId(id);
	}
	
	@GetMapping("/{id}/exhibition")
	public Exhibition getExhibitionByOrgStaff(@PathVariable("id") int id) {
		 
		 return orgStaffService.getExhibitionByOrgStaff(id);
	 }
	
	/**
	 * This is used to delete staff by staff id
	 * @param id
	 * 		staff id
	 */
	@DeleteMapping("/{id}")
	public void deleteStaff(@PathVariable("id") int id) {
		
		orgStaffService.deleteOrgStaff(id);
	}

}
