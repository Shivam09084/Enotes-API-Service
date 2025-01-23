package com.coder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.coder.endpoint.CategoryEndpoint;
import com.coder.service.CategoryService;
import com.coder.util.CommonUtil;

@RestController
public class CategoryController implements CategoryEndpoint{
	
	@Autowired
	private CategoryService categoryService;
	
	@Override
	public ResponseEntity<?> saveCategory( CategoryDto categoryDto){
		
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		if(saveCategory) {
//			return new ResponseEntity<>("saved Successfully",HttpStatus.CREATED);
			return CommonUtil.createBuildResponseMessage("Saved Successfully", HttpStatus.CREATED);
		}else {
//			return new ResponseEntity<>("no saved",HttpStatus.INTERNAL_SERVER_ERROR);
			return CommonUtil.createErrorResponseMessage("Category Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@Override
	public ResponseEntity<?> getAllCategory(){
		
		
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}else {
//		return new ResponseEntity<>(allCategory,HttpStatus.OK);
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<?> getActiveCategory(){
		
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}else {
//		return new ResponseEntity<>(allCategory,HttpStatus.OK);
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<?> getCategoryDetailsById( Integer id) throws Exception{
		
		CategoryDto categoryById = categoryService.getCategoryById(id);
		if(ObjectUtils.isEmpty(categoryById)) {
//			return new ResponseEntity<>("Internal Server Problem",HttpStatus.NOT_FOUND);
			return CommonUtil.createErrorResponseMessage("Internal Server Problem", HttpStatus.NOT_FOUND);
		}
//		return new ResponseEntity<>(categoryById,HttpStatus.OK);
		return CommonUtil.createBuildResponse(categoryById, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> deleteCategoryById( Integer id){
		Boolean deleteCategory = categoryService.deleteCategory(id);
		if(deleteCategory) {
//			return new ResponseEntity<>("Category Deleted Successfully ",HttpStatus.OK);
			return CommonUtil.createBuildResponseMessage("Category Deleted Success", HttpStatus.OK);
		}
//		return new ResponseEntity<>("Category Not Deleted ",HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage("Category Not Deleted", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
