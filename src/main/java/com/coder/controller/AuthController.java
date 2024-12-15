package com.coder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coder.dto.LoginRequest;
import com.coder.dto.LoginResponse;
import com.coder.dto.UserRequest;
import com.coder.service.UserService;
import com.coder.util.CommonUtil;

import ch.qos.logback.core.net.LoginAuthenticator;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto, HttpServletRequest request) throws Exception{
		
		String url = CommonUtil.getUrl(request);
		Boolean register = userService.register(userDto,url);
		if(register) {
			return CommonUtil.createBuildResponseMessage("Register Successfully", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMessage("Register not saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception{
		
		LoginResponse loginResponse = userService.login(loginRequest);
		if(ObjectUtils.isEmpty(loginResponse)) {
			return CommonUtil.createErrorResponseMessage("Invalid crendtial", HttpStatus.BAD_REQUEST);
		}
		return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
	}
}
