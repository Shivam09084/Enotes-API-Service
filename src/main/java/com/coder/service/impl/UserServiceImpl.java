package com.coder.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.coder.dto.EmailRequest;
import com.coder.dto.UserDto;
import com.coder.entity.Role;
import com.coder.entity.User;
import com.coder.repository.RoleRepository;
import com.coder.repository.UserRepository;
import com.coder.service.UserService;
import com.coder.util.Validation;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public Boolean register(UserDto userDto) throws Exception {
		
		validation.userValidation(userDto);
		User user = mapper.map(userDto, User.class);
		
		setRole(userDto, user);
		User  saveUser = userRepo.save(user);
		if(!ObjectUtils.isEmpty(saveUser)) {
			
			emailSend(saveUser);
			return true;
		}
		return false;
	}

	private void emailSend(User saveUser) throws Exception {
		
		String message = "Hi , <b>"+saveUser.getFirstName()+"</b>"
									+"<br> Your Account Register Successfully.<br>"
									+"<br> click the below link verify your Account <br>"
									+"<a href='#'> click here </a>"
									+"<br> Thanks & Regards<br>"
									+"<br> Enotes<br>";
		
		EmailRequest emailRequest = new EmailRequest().builder()
									.to(saveUser.getEmail())
									.title("Account Creation ")
									.subject("Registeration Successfully")
									.message(message)
									.build();
		emailService.sendEmail(emailRequest);
	}
	private void setRole(UserDto userDto, User user) {
		
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r->r.getId()).toList();
		List<Role> roles = roleRepo.findAllById(reqRoleId);
		user.setRoles(roles);
	}
}
