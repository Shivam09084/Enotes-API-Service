package com.coder.service.impl;



import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.coder.dto.CategoryDto;
import com.coder.dto.CategoryResponse;
import com.coder.entity.Category;
import com.coder.exception.ExistDataException;
import com.coder.exception.ResourceNotFoundException;
import com.coder.repository.CategoryRepository;
import com.coder.service.CategoryService;
import com.coder.util.Validation;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Validation validation;
	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		
//		Category category = new Category();
//		category.setName(categoryDto.getName());
//		category.setDescription(categoryDto.getDescription());
//		category.setIsActive(categoryDto.getIsActive());
		
		//use for validation 
		validation.categoryValidation(categoryDto);
		// this is used for check name is already exist
//		Category existingCategory=categoryRepo.findByName(categoryDto.getName());
//		if(existingCategory != null && (categoryDto.getId() == null || !existingCategory.getId().equals(categoryDto.getId()))) {
//			throw new IllegalArgumentException("Name is Already Exist");
//		}
		
		Boolean existsByName = categoryRepo.existsByName(categoryDto.getName().trim());
		if(existsByName) {
			throw new ExistDataException("Category is Already Exist");
		}
		
		Category category = mapper.map(categoryDto, Category.class);
		if(ObjectUtils.isEmpty(category.getId())) {
			category.setIsDeleted(false);
			//category.setCreatedBy(1);
			//category.setCreatedOn(new Date());
		}else {
			updateCategory(category);
		}
		Category saveCategory = categoryRepo.save(category);
		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	private void updateCategory(Category category) {
		Optional<Category> findById = categoryRepo.findById(category.getId());
		if(findById.isPresent()) {
			Category existCategory = findById.get();
			category.setCreatedBy(existCategory.getCreatedBy());
			category.setCreatedOn(existCategory.getCreatedOn());
			category.setIsDeleted(existCategory.getIsDeleted());
			
//			category.setUpdatedBy(1);
//			category.setUpdatedOn(new Date());
		}
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepo.findByIsDeletedFalse();
		List<CategoryDto> categoryDtoList = categories.stream().map(cat -> mapper.map(cat, CategoryDto.class)).toList();
		return categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		List<Category> categories = categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> categorylist = categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class)).toList();
		return categorylist;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) throws Exception {
//		Optional<Category> findByCategoey = categoryRepo.findByIdAndIsDeletedFalse(id);
//		if(findByCategoey.isPresent()) {
//			Category category = findByCategoey.get();
//			return mapper.map(category, CategoryDto.class);
//		}
		Category category = categoryRepo.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new  ResourceNotFoundException("Category not found id = "+id));
		if(!ObjectUtils.isEmpty(category)) {
			
			return mapper.map(category, CategoryDto.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCategory(Integer id) {
		Optional<Category> findByCategory = categoryRepo.findById(id);
		if(findByCategory.isPresent()) {
			Category category = findByCategory.get();
			category.setIsDeleted(true);
			categoryRepo.save(category);
			return true;
		}
		return false;
	}
	
}
