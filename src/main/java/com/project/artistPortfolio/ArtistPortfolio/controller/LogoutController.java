package com.project.artistPortfolio.ArtistPortfolio.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;

/**
 * This is used to map logout request
 * @author anjuk
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class LogoutController {
	
	private final static Logger logger = LoggerFactory.getLogger(LogoutController.class);
	
	/**
	 * this is used to map logout request
	 * @param request
	 * @param response
	 * @return message 
	 */
	@GetMapping("/user-logout")
	@CrossOrigin
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("loggin out from the system.......");
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  
		    if (auth != null){    
		        new SecurityContextLogoutHandler().logout(request, response, auth);
		        Jwts.builder().setExpiration(new Date("Thu, 01 Jan 1970 00:00:00 UTC"));      
		    }
		    return "Logged out successfully";	
	}

}
