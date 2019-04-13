package com.project.artistPortfolio.ArtistPortfolio.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface MediaStorageService {
	
	void uploadFile(MultipartFile file,String location) throws IOException;

}
