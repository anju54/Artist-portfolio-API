package com.project.artistPortfolio.ArtistPortfolio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.artistPortfolio.ArtistPortfolio.DTO.LoginDTO;

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
    
    /**
     * Maps user token generation request and extracts user name and password from
     * request.
     * 
     * @param loginUser User model for login
     * @return ResponseEntity
     * @throws AuthenticationException
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody LoginDTO loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		loginUser.getEmail(), loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }
}
