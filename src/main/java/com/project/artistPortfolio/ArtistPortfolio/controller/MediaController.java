package com.project.artistPortfolio.ArtistPortfolio.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.artistPortfolio.ArtistPortfolio.DTO.MediaDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.ArtistProfileRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileService;
import com.project.artistPortfolio.ArtistPortfolio.service.MediaService;
import com.project.artistPortfolio.ArtistPortfolio.service.MediaStorageService;
import com.project.artistPortfolio.ArtistPortfolio.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/media")
public class MediaController {
	
	private final static Logger logger = LoggerFactory.getLogger(MediaController.class);
	
	@Autowired
	private  MediaStorageService fileStorageService;
	
	@Autowired
	private ArtistProfileRepository artistProfileRepository;
	
	@Autowired
	private ArtistProfileService artistProfileService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MediaService mediaService;
	
	@Autowired
	ServletContext servletContext; 
	
	@GetMapping("/{id}")
	public @ResponseBody Media getMediaById(@PathVariable("id") int id) {
		
		logger.info("trying to get painting type");
		return 	mediaService.getMediaById(id);
	}
	
	@PostMapping(value="/upload/profile-pic", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadFile(MultipartFile file,Authentication authentication,HttpServletRequest request) throws IOException {
		
		String filename = file.getOriginalFilename();
		File a = new File(filename);
		logger.info(a.getAbsolutePath());
		logger.info(a.getCanonicalPath());
		logger.info(filename, a.getAbsoluteFile());
		logger.info(filename);
		
		logger.info("context path printing : "+ request.getContextPath() );
		
		//append server IP and port number to read the image
		String uploadLocation = ".././media/artist-profile-pics/";
		
		fileStorageService.uploadFile(file,uploadLocation);
		
		String email = userService.getPrincipalUser(authentication).getUsername();
		UserModel user  = userService.getUserByEmail(email);
		
		ArtistProfile artistProfile =  user.getArtistProfile();
		
		Media media = new Media();
		media.setPath("/media/artist-profile-pics/");
		media.setFileName("profile-pic-"+filename);
		media.setFilenameOriginal(filename);
		
		Media savedMedia = mediaService.createMedia(media);
		
		artistProfile.setMedia(savedMedia);
		artistProfileRepository.save(artistProfile);
		
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@PostMapping(value="/upload/paintings", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadpaintings(MultipartFile file,Authentication authentication) throws IOException {

		String username = userService.getPrincipalUser(authentication).getUsername();
		UserModel user = userService.getUserByEmail(username);
		String profileName = user.getArtistProfile().getProfileName();
		
		String paintingUploadLocation = "../ArtistPortfolioAPI/media/" + profileName;
			
		String filename = file.getOriginalFilename();
		fileStorageService.uploadFile(file,paintingUploadLocation);
		
		MediaDTO mediaDTO = new MediaDTO();
		mediaDTO.setFileName(filename);
		mediaDTO.setPath("/media/"+profileName+"/");
		
		artistProfileService.addArtistProfileMedia(mediaDTO, profileName);
		return null;	
	}
	
	/**
	 * This is used to update profile picture of particular user
	 * @param email
	 * @param file
	 * @throws IOException
	 */
	@PutMapping("/{email}")
	public void updateProfilePic(@PathVariable("email") String email,MultipartFile file) throws IOException {
		
		mediaService.updateProfilePic(email, file);
	}
	
	@GetMapping("/read/image")
	public void readMedia(Authentication authentication,String picType,HttpServletResponse response) throws IOException {
		
		String username = userService.getPrincipalUser(authentication).getUsername();
		UserModel user = userService.getUserByEmail(username);
		String profileName = user.getArtistProfile().getProfileName();
		
		ArtistProfile artistProfile = artistProfileService.getArtistProfileByProfileName(profileName);
		String path = artistProfile.getMedia().getPath();
		String fileName = artistProfile.getMedia().getFileName();
		
		String paintingUploadLocation = path + fileName;
		picType = "profile-pic";
		
		 InputStream in = servletContext.getResourceAsStream(paintingUploadLocation);
		 logger.info(paintingUploadLocation);
		    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		    IOUtils.copy(in, response.getOutputStream());
			
//		if(picType.equalsIgnoreCase("profile-pic")) {
//			 paintingUploadLocation = "../../Artist_portfolio/ArtistPortfolioAPI/media/artist-profile-pics/" +
//					 											"profile-pic-anju.jpg";
//		} else if (picType.equalsIgnoreCase("painting")) {
//			 paintingUploadLocation = "../../Artist_portfolio/ArtistPortfolioAPI/media/" + profileName;
//		}
		
		
//		BufferedImage img = null;
//		try {
//		    img = ImageIO.read(new File(paintingUploadLocation));
//		    return img;
//		} catch (IOException e) {
//		}
//		return img;
		
	}
	
	@PutMapping("/{id}")
	public void update(@PathVariable("id") int id, @RequestBody Media media) {
		
		mediaService.updateMedia(id, media);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") int id) {
		
		mediaService.deleteMediaById(id);
	}
}
