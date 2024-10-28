package com.coder.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.coder.dto.NotesDto;
import com.coder.dto.NotesDto.CategoryDto;
import com.coder.entity.Notes;
import com.coder.exception.ResourceNotFoundException;
import com.coder.repository.CategoryRepository;
import com.coder.repository.NotesRepository;
import com.coder.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService{

	@Autowired
	private NotesRepository notesRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public Boolean saveNotes(NotesDto notesDto) throws Exception {
		// check Category Validation id
		
		checkCategoryExist(notesDto.getCategory());
		
		Notes notes = mapper.map(notesDto, Notes.class);
		Notes  saveNotes = notesRepo.save(notes);
		if(!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}
		return false;
	}

	private void checkCategoryExist(CategoryDto category) throws Exception {
		
		categoryRepo.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("Category Id Is Invalid"));
	}

	@Override
	public List<NotesDto> getAllNotes() {
		
		return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
	}

}
