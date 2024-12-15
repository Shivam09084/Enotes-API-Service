package com.coder.service;

import com.coder.dto.LoginRequest;
import com.coder.dto.LoginResponse;
import com.coder.dto.UserRequest;

public interface UserService {

	public Boolean register(UserRequest userDto, String url) throws Exception;
	public LoginResponse login(LoginRequest loginRequest);
}
