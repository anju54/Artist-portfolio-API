package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.io.File;
import java.time.ZonedDateTime;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.artistPortfolio.ArtistPortfolio.service.MediaStorageService;

@Service
public class MediaStorageServiceImpl implements MediaStorageService{
	
	private final static Logger logger = LoggerFactory.getLogger(MediaStorageServiceImpl.class);

	@Override
	public void uploadFile(MultipartFile file, String location, String fileType) throws IOException {
		
		logger.info("trying to upload file");
		File newFile;
		
		// Create a blank new file in the upload location
		if(fileType.equalsIgnoreCase("profile-pic")) {
			
			 newFile = new File(location + "profile-pic-" + file.getOriginalFilename());
		}else {
			String renameFileName =  String.valueOf(System.currentTimeMillis()/1000)  + file.getOriginalFilename();
			 newFile = new File(location + renameFileName );
//			 newFile.renameTo(renameFileName);
		}
		
		newFile.createNewFile();
		
		// Open output stream to new file and write from file to be uploaded
		FileOutputStream fileOutputStream = new FileOutputStream(newFile);
		fileOutputStream.write(file.getBytes());
		logger.info("closing the file");
		fileOutputStream.close();
	}
}
