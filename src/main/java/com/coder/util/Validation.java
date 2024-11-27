package com.coder.util;

import java.util.LinkedHashMap;
import java.util.Map;


import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.coder.dto.CategoryDto;
import com.coder.dto.TodoDto;
import com.coder.dto.TodoDto.StatusDto;
import com.coder.enums.TodoStatus;
import com.coder.exception.ResourceNotFoundException;
import com.coder.exception.ValidationException;
@Component
public class Validation {
	
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
}
