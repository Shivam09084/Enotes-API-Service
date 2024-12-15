package com.coder.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.coder.config.security.CustomUserDetails;
import com.coder.entity.User;
import com.coder.handler.GenericResponse;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtil {
	
	public static ResponseEntity<?> createBuildResponse(Object date,HttpStatus status){
		
		GenericResponse response = GenericResponse.builder()
									.responseStatus(status)
									.status("success")
									.message("success")
									.data(date)
									.build();
		return response.create();
	}
	
	public static ResponseEntity<?> createBuildResponseMessage(String message,HttpStatus status){
			
			GenericResponse response = GenericResponse.builder()
										.responseStatus(status)
										.status("success")
										.message(message)
										.build();
			return response.create();
		}
	public static ResponseEntity<?> createErrorResponse(Object date,HttpStatus status){
		
		GenericResponse response = GenericResponse.builder()
									.responseStatus(status)
									.status("failed")
									.message("failed")
									.data(date)
									.build();
		return response.create();
	}

	
	public static ResponseEntity<?> createErrorResponseMessage(String message,HttpStatus status){
		
		GenericResponse response = GenericResponse.builder()
									.responseStatus(status)
									.status("failed")
									.message(message)
									.build();
		return response.create();
	}
	
	public static String getContentType(String originalFileName) {
		
		String extension = FilenameUtils.getExtension(originalFileName);
		
		switch(extension) {
		case "pdf" :
			return "application/pdf";
		case "xlsx" :
			return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";
		case "txt" :
			return "text/plan";
		case "png" :
			return "image/png";
		case "jpeg" :
			return "image/jpeg";
		default :
			return "application/octet-stream";
		}
	}
	
	public static String getUrl(HttpServletRequest request) {
		
		String apiUrl = request.getRequestURL().toString();    // http:localhost:8080/api/v1/auth
		apiUrl = apiUrl.replace(request.getServletPath(), ""); // http:localhost:8080
		return apiUrl;
	}
	
	public static User getLoggedInUser() {
		try {

		CustomUserDetails logUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return logUser.getUser();
		
		}catch (Exception e) {
			throw e;
		}
	}
}
