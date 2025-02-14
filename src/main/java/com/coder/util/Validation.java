package com.coder.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.coder.dto.CategoryDto;
import com.coder.dto.TodoDto;
import com.coder.dto.TodoDto.StatusDto;
import com.coder.dto.UserRequest;
import com.coder.enums.TodoStatus;
import com.coder.exception.ExistDataException;
import com.coder.exception.ResourceNotFoundException;
import com.coder.exception.ValidationException;
import com.coder.repository.RoleRepository;
import com.coder.repository.UserRepository;
@Component
public class Validation {
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public void categoryValidation(CategoryDto categoryDto) {
		
		Map<String, Object> error = new LinkedHashMap<>();
		
		if(ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("category Object/Json should not be null or empty ");
		}else {
			
			// validation name field
			if(ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "name field is empty or null");
			}else {
				
				if(categoryDto.getName().length() < 3) {
					error.put("name", "min name length is 3");
				}
				if(categoryDto.getName().length()>100) {
					error.put("name", "max name length will be 10");
				}
			}
			
			// validation description
			if(ObjectUtils.isEmpty(categoryDto.getDescription())) {
				error.put("description", "description field is empty or null");
			}
			
			// validation is Active 
			if(ObjectUtils.isEmpty(categoryDto.getIsActive())) {
				error.put("isActive", "iaActive field is empty or null");
			}else {
				if(categoryDto.getIsActive() != Boolean.TRUE.booleanValue() && categoryDto.getIsActive() != Boolean.FALSE.booleanValue()) {
					error.put("isActive","invalid value isActive fiels ");
				}
			}
		}
		if(!error.isEmpty()) {
			throw new ValidationException( error);
		}
	}
	
	public void todoValidation(TodoDto todo) throws Exception{
		
		StatusDto reqStatus = todo.getStatus();
		Boolean statusFound = false;
		
		for(TodoStatus st : TodoStatus.values()) {
			if(st.getId().equals(reqStatus.getId())) {
				statusFound = true;
			}
		}
		if(!statusFound) {
			throw new ResourceNotFoundException("invalid status");
		}
	}
	
	public void userValidation(UserRequest userDto) {
		
		if(!StringUtils.hasText(userDto.getFirstName())) {
			throw new IllegalArgumentException("first Name is invalid");
		}
		
		if(!StringUtils.hasText(userDto.getLastName())) {
			throw new IllegalArgumentException("Last Name is invalid");
		}
		
		if(!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.EMAIL_REGEX)) {
			throw new IllegalArgumentException("email id is invalid");
		}else {
			// validate email exists
			Boolean existsByEmail = userRepo.existsByEmail(userDto.getEmail());
			if(existsByEmail) {
				throw new ExistDataException("Email is already Exists");
			}
		}
		
		if(!StringUtils.hasText(userDto.getPassword()) || !userDto.getPassword().matches(Constants.PASS_REGEX)) {
			throw new IllegalArgumentException("Password is invalid Format");
		}
		
		if(!StringUtils.hasText(userDto.getMobNo()) || !userDto.getMobNo().matches(Constants.MOBNO_REGEX)) {
			throw new IllegalArgumentException("Mob no is invalid");
		}
		
		if(CollectionUtils.isEmpty(userDto.getRoles())) {
			throw new IllegalArgumentException("role is invalid");
		}else {
			 List<Integer> roleIds = roleRepo.findAll().stream().map(r->r.getId()).toList();
			 List<Integer> invalidReqRoleids = userDto.getRoles().stream().map(r->r.getId()).filter(roleId -> !roleIds.contains(roleId)).toList();
			 
			 if (!CollectionUtils.isEmpty(invalidReqRoleids)) {
				throw new IllegalArgumentException("role is invalid"+invalidReqRoleids);
			}
		}
	}
}
