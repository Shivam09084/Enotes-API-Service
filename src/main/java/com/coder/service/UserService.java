package com.coder.service;

import com.coder.dto.UserDto;

public interface UserService {

	public Boolean register(UserDto userDto) throws Exception;
}
