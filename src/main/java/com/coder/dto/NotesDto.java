package com.coder.dto;

import java.util.Date;

import lombok.Data;

@Data
public class NotesDto {
	
	private Integer id;
	private String title;
	private String description;
	private CategoryDto category;
	private Integer createdBy;
	private Date createdOn;
	private Integer updatedBy;
	private Date updatedOn;
	
	
	@Data
	public static class CategoryDto{
		private Integer id;
		private String name;
	}

}
