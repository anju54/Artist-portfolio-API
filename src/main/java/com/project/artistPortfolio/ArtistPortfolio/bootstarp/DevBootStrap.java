package  com.project.artistPortfolio.ArtistPortfolio.bootstarp;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.artistPortfolio.ArtistPortfolio.model.Role;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.RoleRepository;
import com.project.artistPortfolio.ArtistPortfolio.repository.UserRepository;

@Component
public class DevBootStrap {
	
	UserRepository userRepository;
	RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private final static Logger logger = LoggerFactory.getLogger(DevBootStrap.class);
	
	@PostConstruct
	public void initData() {
		//data();
	}
	
	/***
	 * store the data in db in project startup
	 */
	public void data() {
		logger.info("calling from post construct");
		UserModel user = new UserModel("anju","kumari","anju.k302@gmail.com","Anju123.");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		
		Role admin = new Role("ADMIN");
		Role artist = new Role("ARTIST");
		roleRepository.save(admin);
		roleRepository.save(artist);
	
	logger.info("new record has been inserted in user and role table");
	}

}
