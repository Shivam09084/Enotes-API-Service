package com.coder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.coder.endpoint.HomeEndpoint;
import com.coder.service.HomeService;
import com.coder.service.UserService;
import com.coder.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController

public class HomeController implements HomeEndpoint{

	Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public ResponseEntity<?> verifyUserAccount( Integer userid ,  String code) throws Exception{
		
		log.info("HomeController : verifyUserAccount() : Execution Start");
		Boolean verifyAccount = homeService.verifyAccount(userid, code);
		
		if(verifyAccount) 
			return CommonUtil.createBuildResponseMessage("Account Verifies ", HttpStatus.OK);
		
		log.info("HomeController : verifyUserAccount() : Execution End");
		return CommonUtil.createErrorResponseMessage("Invalid verification link", HttpStatus.BAD_REQUEST);
		
	}
	
	// password reset API
	@Override
	public ResponseEntity<?> sendEmailForPasswordReset( String email , HttpServletRequest request) throws Exception{
		
		userService.sendEmailPasswordReset(email, request);
		return CommonUtil.createBuildResponseMessage("Email Send successfully check for email", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> verifyPasswordResetLink( Integer uid, String code) throws Exception{
		
		userService.verifyPswdResetLink(uid, code);
		return CommonUtil.createBuildResponseMessage("Verification successfully", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> resetPassword ( PswdResetRequest pswdResetRequest) throws Exception{
		
		userService.resetPassword(pswdResetRequest);
		return CommonUtil.createBuildResponseMessage("Password reset success ", HttpStatus.OK);
	}
}
