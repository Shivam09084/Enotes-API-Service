package com.coder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coder.dto.CategoryDto;
import com.coder.dto.CategoryResponse;
import com.coder.service.CategoryService;
import com.coder.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
		
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		if(saveCategory) {
//			return new ResponseEntity<>("saved Successfully",HttpStatus.CREATED);
			return CommonUtil.createBuildResponseMessage("Saved Successfully", HttpStatus.CREATED);
		}else {
//			return new ResponseEntity<>("no saved",HttpStatus.INTERNAL_SERVER_ERROR);
			return CommonUtil.createErrorResponseMessage("Category Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@GetMapping("/")
	public ResponseEntity<?> getAllCategory(){
		
		
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}else {
//		return new ResponseEntity<>(allCategory,HttpStatus.OK);
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
		}
	}
	
	@GetMapping("/active-category")
	public ResponseEntity<?> getActiveCategory(){
		
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}else {
//		return new ResponseEntity<>(allCategory,HttpStatus.OK);
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception{
		
		CategoryDto categoryById = categoryService.getCategoryById(id);
		if(ObjectUtils.isEmpty(categoryById)) {
//			return new ResponseEntity<>("Internal Server Problem",HttpStatus.NOT_FOUND);
			return CommonUtil.createErrorResponseMessage("Internal Server Problem", HttpStatus.NOT_FOUND);
		}
//		return new ResponseEntity<>(categoryById,HttpStatus.OK);
		return CommonUtil.createBuildResponse(categoryById, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
		Boolean deleteCategory = categoryService.deleteCategory(id);
		if(deleteCategory) {
//			return new ResponseEntity<>("Category Deleted Successfully ",HttpStatus.OK);
			return CommonUtil.createBuildResponseMessage("Category Deleted Success", HttpStatus.OK);
		}
//		return new ResponseEntity<>("Category Not Deleted ",HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage("Category Not Deleted", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
