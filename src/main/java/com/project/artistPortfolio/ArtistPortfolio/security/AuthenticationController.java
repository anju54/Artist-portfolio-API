package com.project.artistPortfolio.ArtistPortfolio.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.artistPortfolio.ArtistPortfolio.DTO.LoginDTO;
import com.project.artistPortfolio.ArtistPortfolio.controller.PasswordController;
import com.project.artistPortfolio.ArtistPortfolio.model.UserModel;
import com.project.artistPortfolio.ArtistPortfolio.repository.UserRepository;

/**
 * Implementation for JWT authentication token generation end point. Handles
 * creation of tokens after authentication of user via username and password.
 */
@RestController
@CrossOrigin
@RequestMapping("/auth/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager; // Checks user authentication

    @Autowired
    private JwtTokenUtil jwtTokenUtil;			 // Generates the token

    @Autowired
	private UserRepository userRepository;
    
    private final static Logger logger = LoggerFactory.getLogger(PasswordController.class);
    
    /**
     * Maps user token generation request and extracts user name and password from
     * request.
     * 
     * @param loginUser User model for login
     * @return ResponseEntity
     * @throws AuthenticationException
     */
    @PostMapping(value="/login")
    public ResponseEntity<?> generateToken(@RequestBody LoginDTO loginUser) throws AuthenticationException {
    	
    logger.info("trying to login to the system");

	// Create new authentication token
	UsernamePasswordAuthenticationToken authenticationToken = 
		new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword());
	
	// Set authentication manager with the authentication token
	Authentication authentication = authenticationManager.authenticate(authenticationToken);

	// Set authentication to security context, handles authentication
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
	// Get the user requesting for authentication
	UserModel user = userRepository.findByEmail(loginUser.getEmail());
			
	
	// Generate the JWT token for user
	final String token = jwtTokenUtil.generateToken(user);
	
	return ResponseEntity.ok(new AuthToken(token));
    }

}
