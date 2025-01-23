package com.coder.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coder.dto.PswdResetRequest;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/v1/home")
public interface HomeEndpoint {
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer userid , @RequestParam String code) throws Exception;

	// password reset API
		@GetMapping("/send-email-reset")
		public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email , HttpServletRequest request) throws Exception;
		
		@GetMapping("/verify-pswd-link")
		public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception;
		
		@PostMapping("/reset-pswd")
		public ResponseEntity<?> resetPassword (@RequestBody PswdResetRequest pswdResetRequest) throws Exception;
}
