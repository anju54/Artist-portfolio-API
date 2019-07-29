package com.project.artistPortfolio.ArtistPortfolio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
	return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
	return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.cors().and().csrf().disable();

	http.authorizeRequests().antMatchers("/auth/token/login").permitAll()
		.antMatchers("/api/user/set_password/**").permitAll()
		.antMatchers("/api/artist/registration").permitAll()
		.antMatchers("/media/**").permitAll()
		.antMatchers("/api/media/all/artist/profile-pics/**").permitAll()
		.antMatchers("/api/media/public-albums/artist/**").permitAll()
		.antMatchers("/api/artist-profile/info/**").permitAll()
		.antMatchers("/api/artist-profile/public/profile-pic/**").permitAll()
		.antMatchers("/api/organizer/**").hasAnyAuthority("ROLE_ORGADMIN")
		.antMatchers("/api/exhibition/**").hasAnyAuthority("ROLE_ORGADMIN")
		.antMatchers("/api/organization/**").hasAnyAuthority("ROLE_ORGADMIN")
		.anyRequest().authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
	return new BCryptPasswordEncoder();
    }

}
	