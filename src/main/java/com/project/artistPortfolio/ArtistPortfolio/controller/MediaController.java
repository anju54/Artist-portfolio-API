package com.project.artistPortfolio.ArtistPortfolio.controller;

import java.io.File;
import java.io.IOException;

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

import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.ArtistProfileRepository;
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
	private UserService userService;
	
	@Autowired
	private MediaService mediaService;
	
	@GetMapping("/{id}")
	public @ResponseBody Media getMediaById(@PathVariable("id") int id) {
		
		logger.info("trying to get painting type");
		return 	mediaService.getMediaById(id);
	}
	
	@PostMapping(value="/api/file/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadFile(MultipartFile file,Authentication authentication) throws IOException {
		
		String filename = file.getOriginalFilename();
		File a = new File(filename);
		logger.info(a.getAbsolutePath());
		logger.info(a.getCanonicalPath());
		logger.info(filename, a.getAbsoluteFile());
		logger.info(filename);
		
		fileStorageService.uploadFile(file);
		
		String email = userService.getPrincipalUser(authentication).getUsername();
		UserModel user  = userService.getUserByEmail(email);
		ArtistProfile artistProfile =  user.getArtistProfile();
		
		Media media = new Media();
		media.setPath(MediaStorageService.UPLOAD_LOCATION);
		media.setFileName(filename);
		media.setFilenameOriginal(filename);
		
		mediaService.createMedia(media);
		
		artistProfile.setMedia(media);
		artistProfileRepository.save(artistProfile);
		
		return new ResponseEntity<>( HttpStatus.OK);
	}	
	
//	@PostMapping("/upload")
//	public int create(@RequestBody Media media) {
//		return mediaService.createMedia(media);
//	}
	
	@PutMapping("/{id}")
	public void update(@PathVariable("id") int id, @RequestBody Media media) {
		
		mediaService.updateMedia(id, media);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") int id) {
		
		mediaService.deleteMediaById(id);
	}
}
