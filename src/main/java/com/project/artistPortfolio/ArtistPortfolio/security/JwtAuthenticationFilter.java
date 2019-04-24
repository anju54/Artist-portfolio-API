package com.project.artistPortfolio.ArtistPortfolio.security;

import static com.project.artistPortfolio.ArtistPortfolio.security.Constants.HEADER_STRING;
import static com.project.artistPortfolio.ArtistPortfolio.security.Constants.TOKEN_PREFIX;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * Filters all requests before passing them, the filter is applied before web
 * security.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Implements internal filter of request with the JWT token.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
	    throws IOException, ServletException {
	
	String header = req.getHeader(HEADER_STRING);	// Get request header i.e authorization
	
	String username = null;
	String authToken = null;
	
	// Check if authorization starts with token prefix, then extract authentication
	if (header != null && header.startsWith(TOKEN_PREFIX)) {
	    authToken = header.replace(TOKEN_PREFIX, "");
	    try {
		username = jwtTokenUtil.getUsernameFromToken(authToken);
	    } catch (IllegalArgumentException e) {
		logger.error("an error occured during getting username from token", e);
	    } catch (ExpiredJwtException e) {
		logger.warn("the token is expired and not valid anymore", e);
	    } catch (SignatureException e) {
		logger.error("Authentication Failed. Username or Password not valid.");
	    }
	} else {
	    logger.warn("couldn't find bearer string, will ignore the header");
	}
	if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

	    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

	    if (jwtTokenUtil.validateToken(authToken, userDetails)) {
	    	 UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
             //UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
             authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
             logger.info("authenticated user " + username + ", setting security context");
             SecurityContextHolder.getContext().setAuthentication(authentication);
	    }
	}

	chain.doFilter(req, res);
    }
}