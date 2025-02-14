package com.coder.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.coder.dto.EmailRequest;
import com.coder.dto.PasswordChngRequest;
import com.coder.dto.PswdResetRequest;
import com.coder.entity.User;
import com.coder.exception.ResourceNotFoundException;
import com.coder.repository.UserRepository;
import com.coder.service.UserService;
import com.coder.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EmailService emailService;
	
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
	
// password reset logic implement 
	
	@Override
	public void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception {
		
		User user = userRepo.findByEmail(email);
		
		if(ObjectUtils.isEmpty(user)) {
			throw new ResourceNotFoundException("invalid email");
		}
		
		// Generate uique password
		String passwordResetToken = UUID.randomUUID().toString();
		user.getStatus().setPasswordResetToken(passwordResetToken);
		
		User updateUser = userRepo.save(user);
		
		String url = CommonUtil.getUrl(request);
		sendEmailRequest(updateUser,url);
	}

	private void sendEmailRequest(User user, String url) throws Exception{
		
		String message = "Hi <b>[[username]]</b> " 
                +"<br><p>You have requested to reset your password.</p>"
				 + "<p>Click the link below to change your password:</p>"
				 + "<p><a href=[[url]]>Change my password</a></p>"
				 + "<p>Ignore this email if you do remember your password, "
		         + "or you have not made the request.</p><br>"
		         + "Thanks,<br>Enotes.com";
		
		message = message.replace("[[username]]", user.getFirstName());
		message = message.replace("[[url]]", url+"/api/v1/home/verify-pswd-link?uid=" + user.getId()+ "&&code=" 
												+user.getStatus().getPasswordResetToken());
		
		EmailRequest emailRequest = EmailRequest.builder().to(user.getEmail())
									.title("Password Reset").subject("Password Reset link").message(message).build();
		
		// send password reset email to user
		emailService.sendEmail(emailRequest);
	}

	@Override
	public void verifyPswdResetLink(Integer uid, String code) throws Exception {
		
		User user = userRepo.findById(uid).orElseThrow(()-> new ResourceNotFoundException("invalid User"));
		verifyPasswordResetToken(user.getStatus().getPasswordResetToken(),code);
	}

	private void verifyPasswordResetToken(String existToken, String reqToken) {
		
		// request token not null
		if(StringUtils.hasText(reqToken)) {
			
			// password already reset 
			if(!StringUtils.hasText(existToken)) {
				
				throw new IllegalArgumentException("Already password reset");
			}
			
			// user request token change
			if(!existToken.equals(reqToken)) {
				throw new IllegalArgumentException("invalid url");
			}
		}else {
			throw new IllegalArgumentException("invalid token ");
		}
	}

	@Override
	public void resetPassword(PswdResetRequest pswdResetRequest) throws Exception {
		
		User user = userRepo.findById(pswdResetRequest.getUid()).orElseThrow(()-> new ResourceNotFoundException("invalid user"));
		String encodePassword = passwordEncoder.encode(pswdResetRequest.getNewPassword());
		
		user.setPassword(encodePassword);
		user.getStatus().setPasswordResetToken(null);
		
		userRepo.save(user);
	}
	
	

}
