package com.project.artistPortfolio.ArtistPortfolio.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;

/**
 * This service class is used to send mail with token 
 * @author anjuk
 *
 */
@Component
public class EmailServiceImpl  {
	
	private final static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	 @Autowired
	 public JavaMailSender emailSender;
	 
	 /**
	  * used to structure the email
	  * @param subject
	  * @param body
	  * @param user
	  * @return SimpleMailMessage object.
	  */
	 public SimpleMailMessage constructEmail(String subject, String body, UserModel user) {
	 
		    SimpleMailMessage email = new SimpleMailMessage();
		    email.setSubject(subject);
		    email.setText(body);
		    email.setTo(user.getEmail());
		    email.setFrom("anju.k302@gmail.com");
		    
		    logger.info("mail has been sent to "+user.getEmail());
		    return email;
	 }
	 
	 /**
	  * used for sending the email with login detail after the user registration
	  * @param User object
	  * @param password
	  * @return link SimpleMailMessage object
	  */
	 public SimpleMailMessage registrationCredentialEmail( UserModel user,String token) {
		 	
		    String body = " http://127.0.0.1:8085/artist_portfolio/artist_portfolio/resetpassword.html?token="+token+"&id="+user.getId();
		    
		    return constructEmail("set password", "click here to set password  " + body , user);
	 }	 	
}
