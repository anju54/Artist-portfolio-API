package com.project.artistPortfolio.ArtistPortfolio.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.project.artistPortfolio.ArtistPortfolio.DTO.ArtistProfilePic;
import com.project.artistPortfolio.ArtistPortfolio.DTO.MediaArtistDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.MediaDTO;
import com.project.artistPortfolio.ArtistPortfolio.DTO.PaintingsDTO;
import com.project.artistPortfolio.ArtistPortfolio.exception.FileExtensionNotValidException;
import com.project.artistPortfolio.ArtistPortfolio.exception.FileNotFound;
import com.project.artistPortfolio.ArtistPortfolio.exception.FileSizeExceeded;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfile;
import com.project.artistPortfolio.ArtistPortfolio.model.ArtistProfileMedia;
import com.project.artistPortfolio.ArtistPortfolio.model.Media;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.ArtistProfileMediaRepository;
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
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ArtistProfileMediaRepository artistProfileMediaRepository;
	
	 
	 private Pattern pattern;
	
	 private Matcher matcher;
	 
	 private static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|jpeg|bmp))$)";
	 
	 public static final long TEN_MB_IN_BYTES = 10485760;
		
	
//	@GetMapping("/artist/albums/{pageNo}/{pageLimit}/{id}")
//	public List<ArtistProfileMedia> getMediaByPageNo(@PathVariable("pageNo") int pageNo,@PathVariable("pageLimit") int pageLimit,@PathVariable("id") int id){
//		
//		//Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
//		
//		List<ArtistProfileMedia> artistProfileMediaList = artistProfileMediaRepository.
//				findArtistProfileMediaByArtistProfileId(id, (Pageable) PageRequest.of(pageNo, pageLimit));
//		return artistProfileMediaList;
//	}
	
	@GetMapping("/all/public/images")
	public List<ArtistProfileMedia> list(@RequestParam("id") int id,@RequestParam("pageNo") int pageNo,@RequestParam("pageLimit") int pageLimit){
		
		return artistProfileMediaRepository.findArtistProfileMediaByArtistProfileIdAndpublicImage(id,(Pageable) PageRequest.of(pageNo, pageLimit));
	}
	
	/**
	 * This is used to get all paintings of particular artist
	 * 
	 * @return list of media object
	 */
	@GetMapping("/artist/albums/{pageNo}/{pageLimit}")
	public List<PaintingsDTO> getMediaByArtistProfileMediaKey(Authentication authentication,Pageable pageable,@PathVariable("pageNo") int pageNo,@PathVariable("pageLimit") int pageLimit){
		
		return mediaService.getMediaByArtistProfileMediaKey(authentication,pageNo,pageLimit);
	}
	
	@GetMapping("/{id}")
	public @ResponseBody Media getMediaById(@PathVariable("id") int id) {
		
		logger.info("trying to get painting type");
		return 	mediaService.getMediaById(id);
	}
	

	/**
	 * This is used to get all paintings of particular artist
	 * 
	 * @return list of media object
	 */
	@GetMapping("/public-albums/artist")
	public MediaArtistDTO getPublicMedia(@RequestParam("id") int artistProfileId,Pageable pageable,@RequestParam("pageNo") int pageNo,@RequestParam("pageLimit") int pageLimit){
		
		return mediaService.getPublicMedia(artistProfileId,pageNo,pageLimit);
	}
	
	@PostMapping(value="/upload/profile-pic", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadFile(MultipartFile file,Authentication authentication,HttpServletRequest request)  {
		
		try {
			String filename = file.getOriginalFilename();
//			File a = new File(filename);
			
			//append server IP and port number to read the image
			String uploadLocation = "../ArtistPortfolioAPI/media/artist-profile-pics/";
			
			pattern = Pattern.compile(IMAGE_PATTERN);
			matcher = pattern.matcher(file.getOriginalFilename());
			
			if(file.getOriginalFilename().isEmpty()) {
				logger.info("empty file");
				throw new FileNotFound( "file upload required");
			}else if( !matcher.matches() ) {
				logger.info("not matched");
				throw new FileExtensionNotValidException ("invalid file type!! supported file type : jpg, png, bmp ");
			} 
				else if (file.getSize() > TEN_MB_IN_BYTES) {
				logger.info("size exceeded");
				System.out.println(file.getSize());
				throw new FileSizeExceeded( "file size excedded. supported file size upto 10 MB");
			}
			
			String email = userService.getPrincipalUser(authentication).getUsername();
			UserModel user  = userService.getUserByEmail(email);
			
			fileStorageService.uploadFile(file,uploadLocation,user.getId());
			
			ArtistProfile artistProfile =  new ArtistProfile();
			System.out.println(user);
			artistProfile =  user.getArtistProfile();
			
			Media media = new Media();
			media.setPath("/media/artist-profile-pics/");
			media.setFileName("profile-pic-"+user.getId()+"-"+filename);
			media.setFilenameOriginal(filename);
			
			Media savedMedia = mediaService.createMedia(media);
			
			artistProfile.setMedia(savedMedia);
			artistProfileRepository.save(artistProfile);
			
			return new ResponseEntity<>( HttpStatus.OK);
		}
		catch (FileSizeExceeded e) {
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
			// TODO: handle exception
		}
		return null;
	}
	
	@PostMapping(value="/upload/paintings", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadpaintings(MultipartFile file,Authentication authentication) throws FileSizeLimitExceededException  {

		String username = userService.getPrincipalUser(authentication).getUsername();
		UserModel user = userService.getUserByEmail(username);
		String profileName = user.getArtistProfile().getProfileName();
		
		String renameFileName ="";
		
		MediaDTO mediaDTO = new MediaDTO();
		
		
		//String fileType = "paintings";
		//String filename = file.getOriginalFilename();
		String paintingUploadLocation = "../ArtistPortfolioAPI/media/" + profileName +"/";
		
		pattern = Pattern.compile(IMAGE_PATTERN);
		matcher = pattern.matcher(file.getOriginalFilename());
		
		try {
			
			File newFile;
			
			if(file.getOriginalFilename().isEmpty()) {
				logger.info("empty file");
				throw new FileNotFound( "file upload required");
			}else if( !matcher.matches() ) {
				logger.info("not matched");
				throw new FileExtensionNotValidException ("invalid file type!! supported file type : jpg, png, bmp ");
			}
			 else if (file.getSize() > TEN_MB_IN_BYTES) {
				logger.info("size exceeded");
				System.out.println(file.getSize());
				throw new FileSizeExceeded( "file size excedded. supported file size upto 10 MB");
			}
			
			renameFileName =  String.valueOf(System.currentTimeMillis()/1000)  + file.getOriginalFilename();
			newFile = new File(paintingUploadLocation + renameFileName );
			newFile.createNewFile();
				
			// Open output stream to new file and write from file to be uploaded
			FileOutputStream fileOutputStream = new FileOutputStream(newFile);
			fileOutputStream.write(file.getBytes());
			logger.info("closing the file");
			fileOutputStream.close();
			
			mediaService.thumnailOfImage(paintingUploadLocation, renameFileName);
			
			mediaDTO.setFileName( renameFileName );
			mediaDTO.setPath("/media/"+profileName+"/");
			
			artistProfileService.addArtistProfileMedia(mediaDTO, profileName);
			
		}catch (FileExtensionNotValidException e) {
			logger.info(e.getMessage());	
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid file type!! supported file type : jpg, png, bmp");
		}catch (FileNotFound e) {
			logger.info(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,  "file upload required");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		//fileStorageService.uploadFile(file,paintingUploadLocation,fileType);
	
		return null;	
	}
	
	/**
	 * This is used to set image is public or private
	 * @param publicImage
	 * @param file
	 * 	
	 */
	@PutMapping("/isPublic/{isPublic}/{id}")
	public void setPublicOrPrivateImage(@PathVariable("isPublic") String publicImage,@PathVariable("id") int id) {
		
		mediaService.setPublicOrPrivateImage(publicImage, id);		
	}
	
	/**
	 * This is used to update profile picture of particular user
	 * @param email
	 * @param file
	 * @throws IOException
	 */
	@PutMapping("/profile-pic")
	public void updateProfilePic(Authentication authentication,MultipartFile file) throws IOException {
		
		mediaService.updateProfilePic(authentication, file);
	}
	
	/**
	 * This is used to get all the profile pic of artist
	 * @return List of media object
	 */
	@GetMapping("/all/artist/profile-pics/{pageNo}/{pageLimit}")
	public List<ArtistProfilePic> getAllProfilePicOfArtist(@PathVariable("pageNo") int pageNo, @PathVariable("pageLimit") int pageLimit){
		return mediaService.getAllProfilePicOfArtist(pageNo,pageLimit);
	}
	
	@PutMapping("/{id}")
	public void update(@PathVariable("id") int id, @RequestBody Media media) {
		
		mediaService.updateMedia(id, media);
	}
	
	@DeleteMapping("/profile-pic/{email}")
	public String deleteProfilePic(@PathVariable("email") String email) {
		
		ArtistProfile artistProfile = userService.getUserByEmail(email).getArtistProfile();
		mediaService.deleteMediaById(artistProfile.getMedia().getId());
		
		artistProfile.setMedia(null);
		artistProfileRepository.save(artistProfile);
		
		return "profile pic deleted.";
	}
	
//	@GetMapping("/artist-test/albums/{pageNo}/{pageLimit}")
//	public List<PaintingsDTO> getMediaWithSettings(Authentication authentication,Pageable pageable,@PathVariable("pageNo") int pageNo,@PathVariable("pageLimit") int pageLimit){
//		
//		List<PaintingsDTO> dtos = new ArrayList<PaintingsDTO>();
//		
//		int artistProfileId = userService.getUserByEmail( userService.getPrincipalUser
//				(authentication).getUsername() ).getArtistProfile().getId();
//		
//		List<ArtistProfileMedia> artistProfileMediaList = artistProfileMediaRepository.
//				findArtistProfileMediaByArtistProfileId(artistProfileId, (Pageable) PageRequest.of(pageNo, pageLimit));
//		
//		for(ArtistProfileMedia artistProfileMedia: artistProfileMediaList) {
//			
//			PaintingsDTO dto = new PaintingsDTO();
//			dto.setMedia(artistProfileMedia.getMedia());
//			dto.setPublicImage(artistProfileMedia.getPublicImage());
//			dtos.add(dto);
//		}
//		return dtos;
//		
//	}
	
	@GetMapping("/test/{id}")
	public List<ArtistProfileMedia> test(@PathVariable("id") int id){
		int artistProfileId = id;
		int pageNumber = 1;
		int pageSize = 2;
		Query query = entityManager.createQuery("From ArtistProfileMedia apm where apm.artistProfileMediaKey.artistProfileId=:arg1");
		query.setParameter("arg1", artistProfileId);
		query.setFirstResult((pageNumber-1) * pageSize); 
		query.setMaxResults(pageSize);
		List <ArtistProfileMedia> fooList = query.getResultList();
		return fooList;
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		
		return mediaService.deleteMediaById(id);
	}
}
