package com.coder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coder.dto.TodoDto;
import com.coder.endpoint.TodoEndpoint;
import com.coder.service.TodoService;
import com.coder.util.CommonUtil;

@RestController
public class TodoController implements TodoEndpoint{

	@Autowired
	private TodoService todoService;
	
	@Override
	public ResponseEntity<?> saveTodo( TodoDto todo) throws Exception{
		
		Boolean saveTodo = todoService.saveTodo(todo);
		if(saveTodo) {
			return CommonUtil.createBuildResponseMessage("Todo Saved Successfully", HttpStatus.CREATED);
		}else {
			return CommonUtil.createErrorResponseMessage("Todo not Save", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public ResponseEntity<?> getTodo( Integer id) throws Exception{
		
		TodoDto todoById = todoService.getTodoById(id);
		return CommonUtil.createBuildResponse(todoById, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getAllTodoByUser() throws Exception{
		
		List<TodoDto> todoList = todoService.getTodoByUser();
		if(CollectionUtils.isEmpty(todoList)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(todoList, HttpStatus.OK);
	}
}
