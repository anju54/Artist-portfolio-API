package com.project.artistPortfolio.ArtistPortfolio.service.Impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.project.artistPortfolio.ArtistPortfolio.DTO.ArtistProfilePic;
import com.project.artistPortfolio.ArtistPortfolio.DTO.MediaArtistDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.PaintingsDTO;
import com.project.artistPortfolio.ArtistPortfolio.exception.CustomException;
import com.project.artistPortfolio.ArtistPortfolio.exception.ExceptionMessage;
import com.project.artistPortfolio.ArtistPortfolio.exception.FileExtensionNotValidException;
import com.project.artistPortfolio.ArtistPortfolio.exception.FileNotFound;
import com.project.artistPortfolio.ArtistPortfolio.exception.FileSizeExceeded;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMedia;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.ArtistProfileMediaRepository;
import com.project.artistPortfolio.ArtistPortfolio.repository.MediaRepository;
import com.project.artistPortfolio.ArtistPortfolio.service.ArtistProfileService;
import com.project.artistPortfolio.ArtistPortfolio.service.MediaService;
import com.project.artistPortfolio.ArtistPortfolio.service.MediaStorageService;
import com.project.artistPortfolio.ArtistPortfolio.service.UserService;

/**
 * This service class is used to map all the request related to media
 * @author anjuk
 *
 */
@Service
public class MediaServiceImpl implements MediaService{
	
	@Autowired
	private MediaRepository mediaRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ArtistProfileService artistProfileService;
	
	@Autowired
	private ArtistProfileMediaRepository artistProfileMediaRepository;
		
	@Autowired
	private  MediaStorageService fileStorageService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private Pattern pattern;
		
	private Matcher matcher;
	 
	private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|jpeg|bmp))$)";
	 
	public static final long TEN_MB_IN_BYTES = 10485760;
	
	private final static Logger logger = LoggerFactory.getLogger(MediaServiceImpl.class);
	
	/**
	 * This is used to get media by media id
	 * @param id
	 * 			media id
	 * @return Media object
	 */
	@Override
	public Media getMediaById(int id) {
		
		try {
			Media media = mediaRepository.findById(id).get();
			return media;
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * This is used to get all the profile pic of artist
	 * @return List of media object
	 */
	public List<ArtistProfilePic> getAllProfilePicOfArtist(int pageNo,int pageLimit) {
		
		List<ArtistProfilePic> allArtistProfilePic = new ArrayList<ArtistProfilePic>();
		
		List<Integer> listOfIds = artistProfileService.getAllArtistId(pageNo,pageLimit);
		for(int id: listOfIds) {
			
			Media media = artistProfileService.getArtistProfileById(id).getMedia();
			
			ArtistProfilePic artistProfilePic = new ArtistProfilePic();
			
			artistProfilePic.setMedia(media);
			artistProfilePic.setProfileName(artistProfileService.getArtistProfileById(id).getProfileName());
			UserModel user = artistProfileService.getArtistProfileById(id).getUser();
			artistProfilePic.setFullName(user.getFname()+" "+user.getLname());
			artistProfilePic.setArtistProfileId(id);
			
			allArtistProfilePic.add(artistProfilePic);
		}
		return allArtistProfilePic;
	}
	
	/**
	 * This is used to get all public and private paintings of particular artist 
	 * 
	 * @return list of PaintingsDTO object
	 */
	public List<PaintingsDTO> getMediaByArtistProfileMediaKey(Authentication authentication,int pageNo,int pageLimit) {
		
		List<PaintingsDTO> dtos = new ArrayList<PaintingsDTO>();
		
		int artistProfileId = userService.getUserByEmail( userService.getPrincipalUser
				(authentication).getUsername() ).getArtistProfile().getId();
		
		List<ArtistProfileMedia> artistProfileMediaList = getMediaByArtistId(artistProfileId,pageNo, pageLimit);
		
		for(ArtistProfileMedia artistProfileMedia: artistProfileMediaList) {
			
			PaintingsDTO dto = new PaintingsDTO();
			dto.setMedia(artistProfileMedia.getMedia());
			dto.setPublicImage(artistProfileMedia.getPublicImage());
			dtos.add(dto);
		}
		return dtos;
			
	}
		
	/**
	 * This is used to get all public paintings of particular artist
	 * 
	 * @return list of media object
	 */
	public MediaArtistDTO getPublicMedia(int  artistProfileId,int pageNo,int pageLimit){
		
		List<Media> mediaList = new ArrayList<Media>(); 
		
		MediaArtistDTO mediaArtistDTO = new MediaArtistDTO();
		
		List<ArtistProfileMedia> artistProfileMediaList = artistProfileMediaRepository.
				findArtistProfileMediaByArtistProfileIdAndpublicImage(artistProfileId,(Pageable) PageRequest.of(pageNo, pageLimit));

		for(ArtistProfileMedia artistProfileMedia: artistProfileMediaList) {
			
			if(artistProfileMedia.getPublicImage().equalsIgnoreCase("true")) {
				
				mediaList.add(artistProfileMedia.getMedia());
			}
		}
		
		mediaArtistDTO.setMediaList(mediaList);
		mediaArtistDTO.setProfileName(artistProfileService.getArtistProfileById(artistProfileId).getProfileName());
		return mediaArtistDTO;
	}
	
	@Override
	public Media createMedia(Media media) {
		
		mediaRepository.save(media);
		logger.info("data inserted in media table and now prinintg the media id");
		System.out.println(media.getId());
		return media;
	}
	
	/**
	 * This is used to update profile pic of particular user
	 * @param email
	 * @param file
	 * @throws IOException
	 */
	@Override
	public void updateProfilePic(Authentication authentication,MultipartFile file) throws IOException {
		
		UserModel existingUser = userService.getUserByEmail((userService.getPrincipalUser(authentication).getUsername()));
		Media existingMedia = existingUser.getArtistProfile().getMedia();
		
		pattern = Pattern.compile(IMAGE_PATTERN);
		matcher = pattern.matcher(file.getOriginalFilename());
		
		try {
			String filename = file.getOriginalFilename();
			
			if(file.getOriginalFilename().isEmpty()) {
				logger.info("empty file");
				throw new FileNotFound( "file upload required");
			}else if( !matcher.matches() ) {
				logger.info("not matched");
				throw new FileExtensionNotValidException ("invalid file type!! supported file type : jpg, png, jpeg ");
			} 
			else if (file.getSize() > TEN_MB_IN_BYTES) {
				logger.info("size exceeded");
				System.out.println(file.getSize());
				throw new FileSizeExceeded( "file size excedded. supported file size upto 10 MB");
			}
			String uploadLocation = "../ArtistPortfolioAPI/media/artist-profile-pics/";
			
			fileStorageService.uploadFile(file,uploadLocation,existingUser.getId());
			
			existingMedia.setPath("/media/artist-profile-pics/");
			existingMedia.setFileName("profile-pic-"+existingUser.getId()+"-"+filename);
			existingMedia.setFilenameOriginal(filename);
			
			mediaRepository.save(existingMedia);
		
		}catch (FileSizeExceeded e) {
			logger.info("file size exception caught");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "file size excedded. supported file size upto 10 MB");
		}catch (FileExtensionNotValidException e) {
			logger.info(e.getMessage());	
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid file type!! supported file type : jpg, png, bmp");
		}catch (FileNotFound e) {
			logger.info(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,  "file upload required");
		}
			
	}
	
	/**
	 * This is used to set image is public or private
	 * @param publicImage
	 * @param file
	 * 	
	 */
	public void setPublicOrPrivateImage(String publicImage, int id) {
		
		Media media = getMediaById(id);
		
		ArtistProfileMedia artistProfileMedia = media.getArtistProfileMedia();
		artistProfileMedia.setPublicImage(publicImage);
		artistProfileMediaRepository.save(artistProfileMedia);		
	}
	
	@Override
	public void updateMedia(int id,Media media) {
		
		Media existingMedia = getMediaById(id);
		
		if ( existingMedia!=null ) {
			existingMedia.setFileName(media.getFileName());
			existingMedia.setFilenameOriginal(media.getFilenameOriginal());
			existingMedia.setPath(media.getPath());
			existingMedia.setPathThumb(media.getPathThumb());
			mediaRepository.save(existingMedia);
		}	
	}
	
	/**
	 * This is used to get media by file name
	 * @param fileName
	 * @return Media object
	 */
	public Media getMediaDataByFileName(String fileName) {
		try {
			 return mediaRepository.getMediaByFileName(fileName);
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomException(ExceptionMessage.NO_DATA_AVAILABLE, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	/**
	 * This is used for creating thumbnail of images
	 * @param path
	 * 		image location
	 * @param inputFileName
	 * 			image name
	 */
	public void thumnailOfImage(String path,String inputFileName) throws IOException {
		
		File f = new File(path+inputFileName);
		BufferedImage img = ImageIO.read(f); // load image
		BufferedImage thumbImg = Scalr.resize(img, Method.QUALITY,Mode.AUTOMATIC, 200,200, Scalr.OP_ANTIALIAS); 
		  
		  File f2 = new File(path+"thumbnail/"+"thumb"+inputFileName);
		  ImageIO.write(thumbImg, "jpg", f2);	
	}
	
	/***
	 * This is used to delete media by media id
	 * 
	 * @param id
	 * 		media id
	 */
	@Override
	public String deleteMediaById(int id) {
		
		mediaRepository.deleteById(id);
		return "media deleted";
	}
	
	/**
	 * This is used to get ArtistProfileMedia record by ArtistProfile id
	 * @param artistProfileId
	 * @param pageNumber
	 * @param pageSize
	 * @return List of ArtistProfile object
	 */
	public List<ArtistProfileMedia> getMediaByArtistId(int artistProfileId,int pageNumber, int pageSize) {
		
		Query query = entityManager.createQuery("From ArtistProfileMedia apm where apm.artistProfileMediaKey.artistProfileId=:arg1");
		query.setParameter("arg1", artistProfileId);
		query.setFirstResult((pageNumber-1) * pageSize); 
		query.setMaxResults(pageSize);
		List <ArtistProfileMedia> fooList = query.getResultList();
		return fooList;
		
	}

}
