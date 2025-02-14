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
import com.coder.endpoint.AuthEndpoint;
import com.coder.service.AuthService;
import com.coder.util.CommonUtil;

import ch.qos.logback.core.net.LoginAuthenticator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController

public class AuthController implements AuthEndpoint {

	@Autowired
	private AuthService authService;
	
	@Override
	public ResponseEntity<?> registerUser( UserRequest userDto, HttpServletRequest request) throws Exception{
		
		
		String url = CommonUtil.getUrl(request);
		Boolean register = authService.register(userDto,url);
		
		if(!register) {
			log.info("Error : {}"," Registeration failed");
			return CommonUtil.createErrorResponseMessage("Register not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("AuthController : registerUser():  ExecutionStart");
		return CommonUtil.createBuildResponseMessage("Register Successfully", HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<?> login( LoginRequest loginRequest) throws Exception{
		
		LoginResponse loginResponse = authService.login(loginRequest);
		if(ObjectUtils.isEmpty(loginResponse)) {
			return CommonUtil.createErrorResponseMessage("Invalid crendtial", HttpStatus.BAD_REQUEST);
		}
		return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
	}
}
