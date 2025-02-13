package com.coder.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coder.dto.PasswordChngRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "All The User Operation API's")
@RequestMapping("/api/v1/user")
public interface UserEndpoint {

	@Operation(summary = "Get User Profile",tags= {"User"}, description = "Get User Profile")
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile();
	
	
	@Operation(summary = "Password Change",tags= {"User"}, description = "User Account Password Change ")
	@PostMapping("/chng-password")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChngRequest passwordChngRequest);
}
