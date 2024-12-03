package com.coder.service;

import com.coder.entity.User;

public interface JwtService {

	public String generateToken(User user);
}
