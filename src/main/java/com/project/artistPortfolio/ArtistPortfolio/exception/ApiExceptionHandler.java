package com.project.artistPortfolio.ArtistPortfolio.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.artistPortfolio.ArtistPortfolio.DTO.ErrorResponseDTO;

@ControllerAdvice
public class ApiExceptionHandler {  
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {

		String errorMessage = ex.getMessage();
		errorMessage = (null == errorMessage) ? "Internal Server Error" : errorMessage;
		
		 List<String> details = new ArrayList<>();
	     details.add("");

		return new ResponseEntity<ErrorResponseDTO>(
				new ErrorResponseDTO( errorMessage ,details), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ErrorResponseDTO> handleSQLIntegrityConstraintViolationException(Exception ex){
		
		String errorMessage = ex.getMessage();
		errorMessage = (null == errorMessage) ? "Internal Server Error" : errorMessage;

		List<String> details = new ArrayList<>();
	     details.add(ex.getLocalizedMessage());

		return new ResponseEntity<ErrorResponseDTO>(
				new ErrorResponseDTO( errorMessage ,details), HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	// This will handle org.hibernate.exception.ConstraintViolationException
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(Exception ex){
		
		String errorMessage = "This venue is already booked for this date";

		List<String> details = new ArrayList<>();
	     details.add(ex.getLocalizedMessage());

		return new ResponseEntity<ErrorResponseDTO>(
				new ErrorResponseDTO( errorMessage ,details), HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}
	
