package com.coder.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coder.dto.PasswordChngRequest;
import com.coder.dto.UserResponse;
import com.coder.endpoint.UserEndpoint;
import com.coder.entity.User;
import com.coder.service.UserService;
import com.coder.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController implements UserEndpoint{

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserService userService;
	
	@Override
	public ResponseEntity<?> getProfile(){
		
		User loggedInUser = CommonUtil.getLoggedInUser();
		UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
		return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> changePassword( PasswordChngRequest passwordChngRequest){
		
		userService.changePassword(passwordChngRequest);
		return CommonUtil.createBuildResponseMessage("Password change successfully ", HttpStatus.OK);
	}
}
