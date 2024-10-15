package com.coder.entity;

import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseModel {
	
	private Boolean isActive;
	private Boolean isDeleted;
	private Integer createdBy;
	private Date createdOn;
	private Integer updatedBy;
	private Date updatedOn;


}
