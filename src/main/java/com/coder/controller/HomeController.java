package com.coder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coder.dto.PswdResetRequest;
import com.coder.service.HomeService;
import com.coder.service.UserService;
import com.coder.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer userid , @RequestParam String code) throws Exception{
		
		Boolean verifyAccount = homeService.verifyAccount(userid, code);
		
		if(verifyAccount) 
			return CommonUtil.createBuildResponseMessage("Account Verifies ", HttpStatus.OK);
		return CommonUtil.createErrorResponseMessage("Invalid verification link", HttpStatus.BAD_REQUEST);
		
	}
	
	// password reset API
	@GetMapping("/send-email-reset")
	public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email , HttpServletRequest request) throws Exception{
		
		userService.sendEmailPasswordReset(email, request);
		return CommonUtil.createBuildResponseMessage("Email Send successfully check for email", HttpStatus.OK);
	}
	
	@GetMapping("/verify-pswd-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception{
		
		userService.verifyPswdResetLink(uid, code);
		return CommonUtil.createBuildResponseMessage("Verification successfully", HttpStatus.OK);
	}
	
	@PostMapping("/reset-pswd")
	public ResponseEntity<?> resetPassword (@RequestBody PswdResetRequest pswdResetRequest) throws Exception{
		
		userService.resetPassword(pswdResetRequest);
		return CommonUtil.createBuildResponseMessage("Password reset success ", HttpStatus.OK);
	}
}
