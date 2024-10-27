package com.coder.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.coder.handler.GenericResponse;

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
}
