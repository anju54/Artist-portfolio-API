package com.project.artistPortfolio.ArtistPortfolio.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * MediaStorageService is used for uploading the images
 * @author anjuk
 *
 */
public interface MediaStorageService {
	
	void uploadFile(MultipartFile file,String location,int userId) throws IOException;

}
