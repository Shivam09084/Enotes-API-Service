package com.coder.exception;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.coder.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e){
		log.error("GlobalExceptionHandler :: handleException "+e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		// use Generic 
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){
		
		log.error("GlobalExceptionHandler :: handleIllegalArgumentException "+e.getMessage());
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e){	
		log.error("GlobalExceptionHandler :: handleAccessDeniedException "+e.getMessage());
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(SuccessException.class)
	public ResponseEntity<?> handleSuccessException(SuccessException e){		
		log.error("GlobalExceptionHandler :: handleSuccessException "+e.getMessage());
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.OK);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception e){
		log.error("GlobalExceptionHandler :: handleNullPointerException "+e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception e){
		log.error("GlobalExceptionHandler :: handleResourceNotFoundException "+e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException e){
		log.error("GlobalExceptionHandler :: handleValidationException "+e.getMessage());
//		return new ResponseEntity<>(e.getErrors(),HttpStatus.BAD_REQUEST);
		return CommonUtil.createErrorResponse(e.getErrors(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistDataException(ExistDataException e){
		log.error("GlobalExceptionHandler :: handleExistDataException "+e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
		log.error("GlobalExceptionHandler :: handleHttpMessageNotReadableException "+e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e){
		log.error("GlobalExceptionHandler :: handleFileNotFoundException "+e.getMessage());
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e){
		log.error("GlobalExceptionHandler :: handleBadCredentialsException "+e.getMessage());
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
