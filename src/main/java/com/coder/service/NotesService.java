package com.coder.service;

import java.util.List;

import com.coder.dto.NotesDto;

public interface NotesService {

	public Boolean saveNotes(NotesDto notesDto) throws Exception;
	public List<NotesDto> getAllNotes();
}
