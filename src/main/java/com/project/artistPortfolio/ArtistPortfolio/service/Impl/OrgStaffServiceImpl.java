package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.DTO.OrgStaffDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.RegistrationDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.UpdateUserDTO;
import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.OrgStaff;
import com.project.artistPortfolio.ArtistPortfolio.repository.OrgStaffRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.OrgStaffService;
import com.project.artistPortfolio.ArtistPortfolio.service.OrganizationService;
import com.project.artistPortfolio.ArtistPortfolio.service.UserService;

@Service
public class OrgStaffServiceImpl implements OrgStaffService{
	
	private final static Logger logger = LoggerFactory.getLogger(LinksServiceImpl.class);
	
	@Autowired
	private OrgStaffRepository orgStaffRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * This is used to create new orgStaff.
	 * 
	 * @param OrgStaff object
	 */
	@Override
	public void addOrgStaff(OrgStaffDTO orgStaffDTO) {
		
		OrgStaff orgStaff = new OrgStaff();
		
		RegistrationDTO regDTO = new RegistrationDTO();
		regDTO.setEmail(orgStaffDTO.getEmail());
		regDTO.setFname(orgStaffDTO.getfName());
		regDTO.setLname(orgStaffDTO.getlName());
		regDTO.setRoleName(orgStaffDTO.getRoleName());
		userService.createUser(regDTO); // for storing user detail in user table
		System.out.println(orgStaffDTO.getEmail());
		System.out.println(userService.getUserByEmail(orgStaffDTO.getEmail()).getEmail());
		orgStaff.setUser(userService.getUserByEmail(orgStaffDTO.getEmail()));
		orgStaff.setOrganizationId(organizationService.getOrganizationByName(orgStaffDTO.getOrganizationName()).getOrganizationId());
		orgStaffRepository.save(orgStaff);	
	}
	
	/**
	 * This is used to update new orgStaff.
	 * 
	 * @param id
	 * 			orgStaff id.
	 * @param OrgStaff object.
	 */
	public void updateOrgStaff(int id,UpdateUserDTO updateUserDTO) {
		
		OrgStaff existingOrgStaff = orgStaffRepository.findById(id).get();
		userService.updateUser(existingOrgStaff.getUser().getId(), updateUserDTO);
	}
	
	/**
	 * This is used to get OrgStaff by id.
	 * 
	 * @param id
	 * 			orgStaff id.
	 * @return OrgStaff object.
	 */
	public OrgStaffDTO getOrgStaffById(int id) {
		
		OrgStaffDTO orgStaffDTO = new OrgStaffDTO();
		try {
			OrgStaff existingOrgStaff = orgStaffRepository.findById(id).get();
			orgStaffDTO.setfName(existingOrgStaff.getUser().getFname());
			orgStaffDTO.setlName(existingOrgStaff.getUser().getLname());
			orgStaffDTO.setEmail(existingOrgStaff.getUser().getEmail());
			orgStaffDTO.setOrganizationName(organizationService.getOrganizationById(existingOrgStaff.getOrganizationId()).getOrganizationName());
			return orgStaffDTO;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * This is used to get OrgStaff by id.
	 * 
	 * @return List<OrgStaff>.
	 */
	public List<OrgStaffDTO> getallOrgStaff(){
		
		List<OrgStaff> staffList = orgStaffRepository.findAll();
		
		List<OrgStaffDTO> orgStaffDTOs = new ArrayList<OrgStaffDTO>();
		
		for(OrgStaff orgStaff : staffList) {
			
			OrgStaffDTO orgStaffDTO = new OrgStaffDTO();
			
			orgStaffDTO.setOrgStaffId(orgStaff.getId());
			orgStaffDTO.setEmail(orgStaff.getUser().getEmail());
			orgStaffDTO.setfName(orgStaff.getUser().getFname());
			orgStaffDTO.setlName(orgStaff.getUser().getLname());
			orgStaffDTO.setOrganizationName(organizationService.getOrganizationById(orgStaff.getOrganizationId()).getOrganizationName());
			
			orgStaffDTOs.add(orgStaffDTO);
		}
		return orgStaffDTOs;
	}
	
	/**
	 * This is used for deleting the orgStaff.
	 * @param id
	 * 			OrgStaff id
	 */
	public void deleteOrgStaff(int id) {
		
		OrgStaff existingOrgStaff = orgStaffRepository.findById(id).get();
		userService.deleteUser(existingOrgStaff.getUser().getId());
		orgStaffRepository.deleteById(id);
	}

	

}
