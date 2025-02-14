package com.coder.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CategoryDto {

	private Integer id;
	private String name;
	private String description;
	private Boolean isActive;
	private Integer createdBy;
	private Date createdOn;
	private Integer updatedBy;
	private Date updatedOn;
}
