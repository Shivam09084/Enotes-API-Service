package com.coder.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coder.dto.LoginRequest;
import com.coder.dto.UserRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Authentication", description = "All The User Authentication API's")
@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

	
	@ApiResponses(value = {@ApiResponse(responseCode = "201,", description = "Register Successfully"),
							@ApiResponse(responseCode = "500", description = "Internal Server Eroor"), 
							@ApiResponse(responseCode = "400", description = "Bad Request")})
	@Operation(summary = "User Registration Endpoint ", tags = {"Authentication","Home"})
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto, HttpServletRequest request) throws Exception;
	
	
	@Operation(summary = "User Login Endpoint", tags = {"Authentication","Home"})
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception;
}
