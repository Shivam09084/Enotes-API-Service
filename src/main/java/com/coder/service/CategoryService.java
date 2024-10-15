package com.coder.service;

import java.util.List;


import com.coder.entity.Category;


public interface CategoryService {
	
	public Boolean saveCategory(Category category);
	public List<Category> getAllCategory();

}
