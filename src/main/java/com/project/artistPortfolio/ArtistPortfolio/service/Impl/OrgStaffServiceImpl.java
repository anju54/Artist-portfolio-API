package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.model.OrgStaff;
import com.project.artistPortfolio.ArtistPortfolio.repository.OrgStaffRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.OrgStaffService;

@Service
public class OrgStaffServiceImpl implements OrgStaffService{
	
	private final static Logger logger = LoggerFactory.getLogger(LinksServiceImpl.class);
	
	@Autowired
	private OrgStaffRepository orgStaffRepository;
	
	
	/**
	 * This is used to create new orgStaff.
	 * 
	 * @param OrgStaff object
	 */
	@Override
	public void addOrgStaff(OrgStaff orgStaff) {
		
		orgStaffRepository.save(orgStaff);	
	}
	
	/**
	 * This is used to update new orgStaff.
	 * 
	 * @param id
	 * 			orgStaff id.
	 * @param OrgStaff object.
	 */
	public void updateOrgStaff(int id,OrgStaff orgStaff) {
		
//		OrgStaff existingOrgStaff = getOrgStaffById(id);
//		
//		
//		
//		orgStaffRepository.save(existingOrgStaff);
	}
	
	/**
	 * This is used to get OrgStaff by id.
	 * 
	 * @param id
	 * 			orgStaff id.
	 * @return OrgStaff object.
	 */
	public OrgStaff getOrgStaffById(int id) {
		
		try {
			OrgStaff existingOrgStaff = orgStaffRepository.findById(id).get();
			return existingOrgStaff;
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
	public List<OrgStaff> getallOrgStaff(){
		
		return orgStaffRepository.findAll();
	}
	
	/**
	 * This is used for deleting the orgStaff.
	 */
	public void deleteOrgStaff(int id) {
		
		orgStaffRepository.deleteById(id);
	}

}
