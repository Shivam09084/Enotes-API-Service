package com.coder.service;

import java.util.List;

import com.coder.dto.CategoryDto;
import com.coder.dto.CategoryResponse;


public interface CategoryService {
	
	public Boolean saveCategory(CategoryDto categoryDto);
	public List<CategoryDto> getAllCategory();
	public List<CategoryResponse> getActiveCategory();
	public CategoryDto getCategoryById(Integer id);
	public Boolean deleteCategory(Integer id);

}
