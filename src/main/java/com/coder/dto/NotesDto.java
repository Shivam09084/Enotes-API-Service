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
	private FilesDto fileDetails;
	private Boolean isDeleted;
	private Date deletedOn;
	
	@Data
	public static class FilesDto{
		private Integer id;
		private String originalFileName;
		private String displayFileName;
	}
	
	@Data
	public static class CategoryDto{
		private Integer id;
		private String name;
	}

}
