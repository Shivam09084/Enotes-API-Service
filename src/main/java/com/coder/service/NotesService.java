package com.coder.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.coder.dto.FavouriteNoteDto;
import com.coder.dto.NotesDto;
import com.coder.dto.NotesResponse;
import com.coder.entity.FileDetails;

public interface NotesService {

	public Boolean saveNotes(String notes, MultipartFile file) throws Exception;
	public List<NotesDto> getAllNotes();
	public byte[] downloadFile(FileDetails fileDetails) throws Exception;
	public FileDetails getFileDetails(Integer id) throws Exception;
	public NotesResponse getAllNotesByUser( Integer pageNo, Integer pageSize);
	
	// delete Notes Api Method
	
	public void softDeleteNotes(Integer userId) throws Exception;
	public void restoreNotes(Integer userId) throws Exception;
	public List<NotesDto> getUserRecycleBinNotes();
	
	// using Scheduled
	
	public void hardDeleteNotes(Integer id) throws Exception;
	public void emptyRecycleBin();
	
	// adding Favourite Notes logic implement
	
	public void favouriteNotes(Integer noteId)throws Exception;
	public void unFavouriteNotes(Integer noteId)throws Exception;
	public List<FavouriteNoteDto> getUserFavouriteNotes() throws Exception;
	
	// Copy Notes 
	
	public Boolean copyNotes(Integer id) throws Exception;
}
