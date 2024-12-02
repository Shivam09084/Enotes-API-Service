package com.coder.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

	private UserDto user;
	private String token;
}
