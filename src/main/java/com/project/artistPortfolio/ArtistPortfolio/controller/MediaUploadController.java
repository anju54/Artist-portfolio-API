//package com.project.artistPortfolio.ArtistPortfolio.controller;
//
//import java.io.IOException;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.project.artistPortfolio.ArtistPortfolio.service.MediaStorageService;
//
//@RestController
//@CrossOrigin
//public class MediaUploadController {
//	
//	private final static Logger logger = LoggerFactory.getLogger(MediaUploadController.class);
//	
//	@Autowired
//	private  MediaStorageService fileStorageService;
//	
//	@PostMapping(value="/api/file/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ResponseEntity<?> uploadFile(MultipartFile file) throws IOException {
//		
//		String filename = file.getOriginalFilename();
//		logger.info(filename);
//		
//		fileStorageService.uploadFile(file);
//		
//		return new ResponseEntity<>( HttpStatus.OK);
//	}	
//}
