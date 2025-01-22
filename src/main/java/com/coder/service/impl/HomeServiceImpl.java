package com.coder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coder.entity.AccountStatus;
import com.coder.entity.User;
import com.coder.exception.ResourceNotFoundException;
import com.coder.exception.SuccessException;
import com.coder.repository.UserRepository;
import com.coder.service.HomeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HomeServiceImpl implements HomeService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
		
		log.info("HomeServiceImpl : verifyAccount (): Execution Start ");
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("invalid user"));
		
		if(user.getStatus().getVerificationCode() == null) { // this concept is check the after reciving the link the already open and user try to again open i.e. case show exception
			
			log.info("Message : Account Already verified");
			throw new SuccessException("account already verified");
		}
		
		if(user.getStatus().getVerificationCode().equals(verificationCode)) {
			
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			
			userRepo.save(user);
			log.info("message : Account Verification Successfully");
			return true;
		}
		log.info("HomeServiceImpl: verifyAccount () : Execution End");
		return false;
	}

}
