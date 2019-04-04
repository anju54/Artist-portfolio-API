package com.project.artistPortfolio.ArtistPortfolio.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
	
	//Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "D://temp//";
    
    @PostMapping("/upload") 
    public Path singleFileUpload(@RequestParam("file") MultipartFile file) {
    	
    	try {
	    	Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	    	byte[] bytes = file.getBytes();
	    	Files.write(path,bytes);
	    	return path;
	    	
	    	
    	} catch (IOException e) {
            e.printStackTrace();
        }
    	
    	return null;
    }
                               

}
