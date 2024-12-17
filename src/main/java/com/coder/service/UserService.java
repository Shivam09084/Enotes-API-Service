package com.coder.service;

import com.coder.dto.PasswordChngRequest;
import com.coder.dto.PswdResetRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	public void changePassword(PasswordChngRequest passwordChngRequest);
	
	// reset Password
	public void sendEmailPasswordReset(String email , HttpServletRequest request) throws Exception;
	public void verifyPswdResetLink(Integer uid, String code) throws Exception;
	public void resetPassword(PswdResetRequest pswdResetRequest) throws Exception;
}
