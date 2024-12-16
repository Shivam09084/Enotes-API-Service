package com.coder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coder.dto.PasswordChngRequest;
import com.coder.entity.User;
import com.coder.repository.UserRepository;
import com.coder.service.UserService;
import com.coder.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void changePassword(PasswordChngRequest passwordChngRequest) {
		
		User loggedInUser = CommonUtil.getLoggedInUser();
		
		if(!passwordEncoder.matches(passwordChngRequest.getOldPassword(), loggedInUser.getPassword())) {
			throw new IllegalArgumentException("Old Password is Incorrect kindly type a proper old password");
		}
		
		String encodePassword = passwordEncoder.encode(passwordChngRequest.getNewPassword());
		loggedInUser.setPassword(encodePassword);
		userRepo.save(loggedInUser);
	}

}
