package com.coder.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.coder.dto.NotesDto;
import com.coder.dto.NotesResponse;
import com.coder.entity.FileDetails;

public interface NotesService {

	public Boolean saveNotes(String notes, MultipartFile file) throws Exception;
	public List<NotesDto> getAllNotes();
	public byte[] downloadFile(FileDetails fileDetails) throws Exception;
	public FileDetails getFileDetails(Integer id) throws Exception;
	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);
}
