package com.coder.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.coder.dto.TodoDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.coder.util.Constants.ROLE_USER;

@Tag(name = "Todo", description = "All The TODO Operation API's")
@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

	
	@Operation(summary = "save Todo",tags= {"Notes","Todo"}, description = "User save Todo")
	@PostMapping("/")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception;
	
	
	@Operation(summary = "Get Todo By Id",tags= {"Notes","Todo"}, description = "User Get Todo By Id")
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodo(@PathVariable Integer id) throws Exception;
	
	
	@Operation(summary = "Get All Todo",tags= {"Notes","User"}, description = "User Get All Todo ")
	@GetMapping("/list")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllTodoByUser() throws Exception;
	
	
}
