package com.coder.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coder.dto.PswdResetRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Home", description = "All The Home API's")
@RequestMapping("/api/v1/home")
public interface HomeEndpoint {
	
	@Operation(summary = "Verify User Account", tags = {"Home"}, description = "User Account Verification After Registeration Account")
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer userid , @RequestParam String code) throws Exception;

	// password reset API
		@Operation(summary = "Send Email for Password Reset", tags = {"Home"}, description = "User Send email For Password Reset")
		@GetMapping("/send-email-reset")
		public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email , HttpServletRequest request) throws Exception;
		
		
		@Operation(summary = "Verify Password Link", tags = {"Home"}, description = "User Verify Password Link")
		@GetMapping("/verify-pswd-link")
		public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception;
		
		
		@Operation(summary = "Reset Password", tags = {"Home"}, description = "User Reset Password")
		@PostMapping("/reset-pswd")
		public ResponseEntity<?> resetPassword (@RequestBody PswdResetRequest pswdResetRequest) throws Exception;
}
