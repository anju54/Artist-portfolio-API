package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.project.artistPortfolio.ArtistPortfolio.DTO.OrgStaffDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.RegistrationDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.Response;
import com.project.artistPortfolio.ArtistPortfolio.DTO.UpdateUserDTO;
import com.project.artistPortfolio.ArtistPortfolio.exception.ArtistNotFound;
import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.DuplicateRecord;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.exception.FileExtensionNotValidException;
import com.project.artistPortfolio.ArtistPortfolio.exception.FileNotFound;
import com.project.artistPortfolio.ArtistPortfolio.exception.FileSizeExceeded;
import com.project.artistPortfolio.ArtistPortfolio.model.Exhibition;
import com.project.artistPortfolio.ArtistPortfolio.model.Links;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.OrgStaff;
import com.project.artistPortfolio.ArtistPortfolio.model.Organization;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.LinksRepository;
import com.project.artistPortfolio.ArtistPortfolio.repository.OrgStaffRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.ExhibitionService;
import com.project.artistPortfolio.ArtistPortfolio.service.LinksService;
import com.project.artistPortfolio.ArtistPortfolio.service.MediaService;
import com.project.artistPortfolio.ArtistPortfolio.service.MediaStorageService;
import com.project.artistPortfolio.ArtistPortfolio.service.OrgStaffService;
import com.project.artistPortfolio.ArtistPortfolio.service.OrganizationService;
import com.project.artistPortfolio.ArtistPortfolio.service.UserService;

@Service
public class OrgStaffServiceImpl implements OrgStaffService{
	
	private final static Logger logger = LoggerFactory.getLogger(LinksServiceImpl.class);
	
	@Autowired
	private OrgStaffRepository orgStaffRepository;
	
	@Autowired
	private ExhibitionService exhibitionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MediaService mediaService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private LinksService linksService;
	
	@Autowired
	private LinksRepository linksRepository;
	
	@Autowired
	private  MediaStorageService fileStorageService;
	
	private Pattern pattern;
		
	private Matcher matcher;
	 
	private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|jpeg|bmp))$)";
	 
	public static final long TEN_MB_IN_BYTES = 10485760;
	
	
	
	/**
	 * This is used to get org staff's profile picture path by profile id
	 * @param id
	 * 			user id
	 * @return Media object
	 */
	public Media getProfilePicByUserId(int id) {
		try {
			OrgStaff orgStaff = userService.getUserById(id).getOrgStaf();
			if(orgStaff==null) {
				throw new ArtistNotFound("staff account has not been created! create first then proceed with uploading image.");
			}
			
			Media media = orgStaff.getMedia();
			
			if(media==null) {
				throw new FileNotFound("profile pic not uploaded!");
			}
//			File a = new File(media.getPath());
			
			return media;
			
		}catch (FileNotFound e) {
			//throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile pic not uploaded");
			logger.error(e.getMessage());
		}catch (ArtistNotFound e) {
			//throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist account has not been created!create first then proceed with uploading image.");
			logger.info(e.getMessage());
			logger.info("org staff Not found exception caught");
		}
		catch (Exception e) {
			//throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile pic not loaded");
			logger.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * This is used to update profile picture of particular user
	 * @param email
	 * @param file
	 * @return 
	 * @throws IOException
	 */
	public ResponseEntity<?> uploadprofilePicForOrgStaff(Authentication authentication,MultipartFile file) throws IOException {
		
		try {
			String filename = file.getOriginalFilename();
			
			//append server IP and port number to read the image
			String uploadLocation = "../ArtistPortfolioAPI/media/orgstaff-profilepics/";
			
			pattern = Pattern.compile(IMAGE_PATTERN);
			matcher = pattern.matcher(file.getOriginalFilename());
			
			if(file.getOriginalFilename().isEmpty()) {
				logger.info("empty file");
				throw new FileNotFound( "file upload required");
			}else if( !matcher.matches() ) {
				logger.info("not matched");
				throw new FileExtensionNotValidException ("invalid file type!! supported file type : jpg, png, bmp ");
			}else if (file.getSize() > TEN_MB_IN_BYTES) {
				logger.info("size exceeded");
				System.out.println(file.getSize());
				throw new FileSizeExceeded( "file size excedded. supported file size upto 10 MB");
			}
			
			String email = userService.getPrincipalUser(authentication).getUsername();
			UserModel user  = userService.getUserByEmail(email);
			
			OrgStaff orgStaff = user.getOrgStaf();
			
			fileStorageService.uploadFile(file,uploadLocation,user.getId());
			
			Media media = new Media();
			media.setPath("/media/orgstaff-profilepics/");
			media.setFileName("profile-pic-"+user.getId()+"-"+filename);
			media.setFilenameOriginal(filename);
			
			Media savedMedia = mediaService.createMedia(media);
			
			orgStaff.setMedia(savedMedia);
			orgStaffRepository.save(orgStaff);
			
			return new ResponseEntity<>( HttpStatus.OK);
			
		}catch (FileSizeExceeded e) {
			logger.info("file size exception caught");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "file size excedded. supported file size upto 10 MB");
		}
		catch (FileExtensionNotValidException e) {
			logger.info(e.getMessage());	
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid file type!! supported files type are : jpg, png, bmp");
		}catch (FileNotFound e) {
			logger.info(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,  "file upload required");
		}
		catch (Exception e) {
			logger.info(e.getMessage());
		}
		return null;
	}
	
	/**
	 * This is used to create new orgStaff.
	 * 
	 * @param OrgStaffDTO object
	 */
	public void addOrgStaff(OrgStaffDTO orgStaffDTO) {
		
		
		try {
			OrgStaff orgStaff = new OrgStaff();
			
			RegistrationDTO regDTO = new RegistrationDTO();
			regDTO.setEmail(orgStaffDTO.getEmail());
			regDTO.setFname(orgStaffDTO.getfName());
			regDTO.setLname(orgStaffDTO.getlName());
			regDTO.setRoleName(orgStaffDTO.getRoleName());
			
			UserModel existingUser = userService.getUserByEmail(orgStaffDTO.getEmail());
			if(existingUser!=null) {
				throw new DuplicateRecord("staff already exists");
			}
			userService.createUser(regDTO); // for storing user detail in user table
			
			orgStaff.setUser(userService.getUserByEmail(orgStaffDTO.getEmail()));
			orgStaff.setOrganizationId(organizationService.getOrganizationByName(orgStaffDTO.getOrganizationName()).getOrganizationId());
			orgStaffRepository.save(orgStaff);	
			
		}catch (DuplicateRecord e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This staff is already registered!!");
		}
	}
	
	/***
	 * This is used to add organization admin
	 * @param email
	 * 			user email id
	 * @param organizationName
	 * 			organization name
	 */
//	@Override
//	public int addOrgStaffAsAdmin(String organizationName,String email) {
//		
//		int organizationId = organizationService.
//				getOrganizationByName(organizationName).getOrganizationId();
//		logger.info("org id and name"+organizationId+" "+organizationName);
//		UserModel user = userService.getUserByEmail(email);
//		//OrgStaff existingOrgStaff = user.getOrgStaf();
//		OrgStaff orgStaff = new OrgStaff();
//		
//		orgStaff.setOrganizationId(organizationId);
//		orgStaff.setUser(user);
//		orgStaffRepository.save(orgStaff);
//		
//		return organizationId;
//	}

	@Override
	public boolean updateOrgStaff(int id, UpdateUserDTO updateUserDTO) {
		// TODO Auto-generated method stub
		boolean isUpdated = false;
		int userId = orgStaffRepository.findById(id).get().getUser().getId();
		System.out.println(userId);
		userService.updateUser(userId, updateUserDTO);
		isUpdated = true;
		return isUpdated;
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
		Links link = linksRepository.findByRefrenceId(existingOrgStaff.getUser().getId());
		linksService.deleteLink(link.getId());
		userService.deleteUser(existingOrgStaff.getUser().getId());
		
		orgStaffRepository.deleteById(id);
	}

	/**
	 * This is used to get orgStaff by organization id.
	 * 
	 * @param id
	 * 			organization id
	 * @return List<orgStaffDTO>.
	 */
	@Override
	public List<OrgStaffDTO> getStaffListByOrganizationId(int id) {
	
		Organization org =	organizationService.getOrganizationById(id);
		
		List<OrgStaffDTO> orgStaffDTOs = new ArrayList<OrgStaffDTO>();
		
		List<OrgStaff> staffList = org.getOrgStaff();
		
		for(OrgStaff orgStaff : staffList) {
			
			OrgStaffDTO orgStaffDTO = new OrgStaffDTO();
			
			orgStaffDTO.setOrgStaffId(orgStaff.getId());
			orgStaffDTO.setEmail(orgStaff.getUser().getEmail());
			orgStaffDTO.setfName(orgStaff.getUser().getFname());
			orgStaffDTO.setlName(orgStaff.getUser().getLname());
			orgStaffDTO.setOrganizationName(organizationService.getOrganizationById(orgStaff.getOrganizationId()).getOrganizationName());
			orgStaffDTO.setRoleName(orgStaff.getUser().getRole().getRole());
			
			orgStaffDTOs.add(orgStaffDTO);
		}
		return orgStaffDTOs;
	
	}
	
	/**
	 * This is used to get organization detail by org staff id
	 * @param id
	 * 			org admin id (org staff id)
	 * @return Organization object;
	 */
	@Override
	public Organization getOrganizationByOrgStaffId(int id) {  
		
		String orgName = getOrgStaffById(id).getOrganizationName() ;
		Organization org = organizationService.getOrganizationByName(orgName);
		
		return org;
	}
	
	/**
	 * This is used to get organization detail by user id
	 * @param id
	 * 			user id
	 * @return Organization object;
	 */
	@Override
	public Organization getOrganizationByUserId(int id) {  
		
		OrgStaff orgStaff = new OrgStaff();
		try {
			 orgStaff = orgStaffRepository.getOrgStaffByUserId(id);
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.BAD_REQUEST);
		}
		
		Organization org = organizationService.getOrganizationById(orgStaff.getOrganizationId());
		return org;
	}
	
	@Override
	public OrgStaff getStaffByStaffId(int id) {
		
		try {
			OrgStaff orgStaff = orgStaffRepository.findById(id).get();
			return orgStaff;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public Response<OrgStaff> assignStaffForExhibition(String exhibitionTitle,int staffId) {
		
		Response<OrgStaff> returnObject = new Response<>();
		OrgStaff orgStaff = getStaffByStaffId(staffId);
		
		try {
			
			Exhibition exhibition = exhibitionService.getExhibitionByTitle(exhibitionTitle);
			orgStaff.setExhibition(exhibition);
						
			orgStaffRepository.save(orgStaff);
			
			returnObject.setResponse(orgStaff);
			returnObject.setStatus("success");
			return returnObject;
			
		}catch (Exception e) {
			logger.info("error");
			returnObject.setResponse(orgStaff);
			returnObject.setStatus("failed");
			return returnObject;
		}
		
	}

	/**
	 * This is used to get Organizer id by token
	 * @return organizer id
	 */
//	@Override
//	public int getOrgAdminIdbytoken(Authentication authentication) {
//		
//		try {
//		UserModel user = userService.getUserByEmail( userService.getPrincipalUser(authentication).getUsername() );
//		return user.getOrgStaf().getId();
//		}catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Organizer not found!!");
//		}
//	}

}
