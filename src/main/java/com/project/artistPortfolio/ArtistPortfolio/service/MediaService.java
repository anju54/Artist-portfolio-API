package com.project.artistPortfolio.ArtistPortfolio.service;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import com.project.artistPortfolio.ArtistPortfolio.DTO.ArtistProfilePic;
import com.project.artistPortfolio.ArtistPortfolio.DTO.MediaArtistDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.PaintingsDTO;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;

/**
 * MediaService is used to handle all the opeartion related to Media.
 * @author anjuk
 *
 */
public interface MediaService {
	
	Media getMediaById(int id);
	Media createMedia(Media media);
	void updateMedia(int id,Media media);
	String deleteMediaById(int id);
	
	void updateProfilePic(Authentication authentication,MultipartFile file) throws IOException;
	
	List<PaintingsDTO> getMediaByArtistProfileMediaKey(Authentication authentication,int pageno,int pageLimit);
	
	MediaArtistDTO getPublicMedia(int artistProfileId,int pageno,int pageLimit);
	
	void setPublicOrPrivateImage(String publicImage,int id);
	
	public List<ArtistProfilePic> getAllProfilePicOfArtist(int pageNo,int pageLimit);
	
	public void thumnailOfImage(String path,String inputFileName) throws IOException;

}
