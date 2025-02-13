package com.coder.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coder.dto.CategoryDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.coder.util.Constants.ROLE_ADMIN;
import static com.coder.util.Constants.ROLE_ADMIN_USER;

@Tag(name = "Category" , description = "All The Category Operation API's")
@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

	
	@Operation(summary = "Save Category" ,tags = {"Category"}, description = "Admin Save Category")
	@PostMapping("/save-category")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);
	
	
	@Operation(summary = "Get All Category" ,tags = {"Category"}, description = "Admin Get All Category")
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllCategory();
	
	
	@Operation(summary = "Actiive Category" ,tags = {"Category"}, description = "Admin , User Get Active Category")
	@GetMapping("/active-category")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getActiveCategory();
	
	
	@Operation(summary = "Get Category By Id" ,tags = {"Category"}, description = "Admin Get Category By Id")
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception;
	
	
	@Operation(summary = "Delte Category By ID" ,tags = {"Category"}, description = "Admin Delete Category By Id")
	@DeleteMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id);
}
