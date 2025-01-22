package com.coder.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.coder.config.security.CustomUserDetails;
import com.coder.dto.EmailRequest;
import com.coder.dto.LoginRequest;
import com.coder.dto.LoginResponse;
import com.coder.dto.UserRequest;
import com.coder.dto.UserResponse;
import com.coder.entity.AccountStatus;
import com.coder.entity.Role;
import com.coder.entity.User;
import com.coder.repository.RoleRepository;
import com.coder.repository.UserRepository;
import com.coder.service.JwtService;
import com.coder.service.AuthService;
import com.coder.util.Validation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

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
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public Boolean register(UserRequest userDto,String url) throws Exception {
		
		log.info("AuthServiceImpl : register() : Execution Start");
		validation.userValidation(userDto);
		User user = mapper.map(userDto, User.class);
		
		setRole(userDto, user);
		
		AccountStatus status = AccountStatus.builder()   // this code set the status means in this code help we send verification link and save link
								.isActive(false)
								.verificationCode(UUID.randomUUID().toString())
								.build();
		user.setStatus(status);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User  saveUser = userRepo.save(user);
		if(ObjectUtils.isEmpty(saveUser)) {
			
			log.info("AuthServiceImpl : Error : {} "," User not saved");
			return false;
		}
		log.info("AuthServiceImpl : Message  : {} "," User Registration Successfully");
		emailSendForRegister(saveUser,url);
		log.info("AuthServiceImpl : Message : {} "," Email Send Successfully");
		log.info("AuthServiceImpl : register () : Execution End");
		return true;
	}

	private void emailSendForRegister(User saveUser,String url) throws Exception {
		
		String message = "Hi,<b>[[username]]</b> "
							+ "<br> Your account register sucessfully.<br>"
							+"<br> Click the below link verify & Active your account <br>"
							+"<a href='[[url]]'> Click Here </a> <br><br>"
							+"Thanks,<br>Enotes.com"
							;
		
		message = message.replace("[[username]]", saveUser.getFirstName());
		message = message.replace("[[url]]", url + "/api/v1/home/verify?userid=" + saveUser.getId() + "&&code=" + saveUser.getStatus().getVerificationCode());
		
		EmailRequest emailRequest = new EmailRequest().builder()
									.to(saveUser.getEmail())
									.title("Account Creation ")
									.subject("Registeration Successfully")
									.message(message)
									.build();
		emailService.sendEmail(emailRequest);
	}
	private void setRole(UserRequest userDto, User user) {
		
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r->r.getId()).toList();
		List<Role> roles = roleRepo.findAllById(reqRoleId);
		user.setRoles(roles);
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		
		if(authenticate.isAuthenticated()) {
			
			CustomUserDetails customUserDetails = (CustomUserDetails)authenticate.getPrincipal();
			String token=jwtService.generateToken(customUserDetails.getUser());
			
			LoginResponse loginResponse = LoginResponse.builder()
					.user(mapper.map(customUserDetails.getUser(), UserResponse.class))
					.token(token)
					.build();
			return loginResponse;
		}
		return null;
	}
	
	
}
