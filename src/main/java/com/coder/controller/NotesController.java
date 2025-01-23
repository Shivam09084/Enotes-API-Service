package com.coder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coder.dto.FavouriteNoteDto;
import com.coder.dto.NotesDto;
import com.coder.dto.NotesResponse;
import com.coder.endpoint.NotesEndpoint;
import com.coder.entity.FileDetails;
import com.coder.service.NotesService;
import com.coder.util.CommonUtil;

@RestController

public class NotesController implements NotesEndpoint {
	
	@Autowired
	private NotesService notesService;
	
//	@PostMapping("/save")
//	@PreAuthorize("hasRole('USER')")
	
	@Override
	public ResponseEntity<?> saveNotes( String notes,  MultipartFile file) throws Exception{
		
		Boolean saveNotes = notesService.saveNotes(notes, file);
		if(saveNotes) {
			return CommonUtil.createBuildResponseMessage("Notes Saved Successfully", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMessage("Notes Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
//	@GetMapping("/")
//	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public ResponseEntity<?> getAllNotes(){
		List<NotesDto> allNotes = notesService.getAllNotes();
		if(CollectionUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(allNotes, HttpStatus.OK);
	}
	
	// Search Notes 
//	@GetMapping("/search")
//	@PreAuthorize("hasRole('USER')")
	@Override
	public ResponseEntity<?> searchNotes( String key,Integer pageNo, Integer pageSize){
		
		NotesResponse notes = notesService.getNotesByUserSearch(pageNo, pageSize, key);
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	// download Notes
//	 @GetMapping("/download/{id}")
//	 @PreAuthorize("hasAnyRole('ADMIN','USER')")
	@Override
	 public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{
		 
		 FileDetails fileDetails = notesService.getFileDetails(id);
		 byte[] data = notesService.downloadFile(fileDetails);
		 
		 HttpHeaders headers = new HttpHeaders();
		 String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		 headers.setContentType(MediaType.parseMediaType(contentType));
		 headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		 
		 return ResponseEntity.ok().headers(headers).body(data);
	 }
	 
	 // Pagination Notes
//	 @GetMapping("/user-notes")
//	 @PreAuthorize("hasRole('USER')")
	@Override
	 public ResponseEntity<?> getAllNotesByUser(Integer pageNo, Integer pageSize)
	 {
		 
		 NotesResponse notesByUser = notesService.getAllNotesByUser( pageNo, pageSize);
		 
		 return CommonUtil.createBuildResponse(notesByUser, HttpStatus.OK);
	 }

	 // delete Related API
//	 @GetMapping("/delete/{id}")
//	 @PreAuthorize("hasRole('USER')")
	@Override
	 public ResponseEntity<?> deleteNotes( Integer id) throws Exception{
		 notesService.softDeleteNotes(id);
		 return CommonUtil.createBuildResponseMessage("Note Delete Successfully", HttpStatus.OK);
	 }
	 
//	 @GetMapping("/restore/{id}")
//	 @PreAuthorize("hasRole('USER')")
	@Override
	 public ResponseEntity<?> restoreNotes( Integer id) throws Exception{
		 notesService.restoreNotes(id);
		 return CommonUtil.createBuildResponseMessage("Notes Restore Successfully", HttpStatus.OK);
	 }
	 
//	 @GetMapping("/recycle-bin")
//	 @PreAuthorize("hasRole('USER')")
	@Override
	 public ResponseEntity<?> getUserRecycleBinNotes(){
		 
		 List<NotesDto> notes = notesService.getUserRecycleBinNotes();
		 
		 if(CollectionUtils.isEmpty(notes)) {
			 return CommonUtil.createBuildResponseMessage("Notes Not available in Recycle Bin", HttpStatus.OK);
		 }
		 return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	 }
	 
//	 @DeleteMapping("/delete/{id}")
//	 @PreAuthorize("hasRole('USER')")
	@Override
	 public ResponseEntity<?> hardDeleteNotes( Integer id) throws Exception{
		 
		 notesService.hardDeleteNotes(id);
		 return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
	 }
	 
//	 @DeleteMapping("/delete")
//	 @PreAuthorize("hasRole('USER')")
	@Override
	 public ResponseEntity<?> emptyRecycleBin() {
		 
		 
		 notesService.emptyRecycleBin();
		 return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
	 }
	 
	 // favourite Note Related API
	 
//	 @GetMapping("/fav/{noteId}")
//	 @PreAuthorize("hasRole('USER')")
	@Override
	 public ResponseEntity<?> favouriteNote( Integer noteId ) throws Exception{
		 
		 notesService.favouriteNotes(noteId);
		 return CommonUtil.createBuildResponseMessage("Favourite "+noteId+" Note Added Successfully", HttpStatus.CREATED);
	 }
	 
//	 @DeleteMapping("/unfav/{favNoteId}")
//	 @PreAuthorize("hasRole('USER')")
	@Override
	 public ResponseEntity<?> unFavouriteNote( Integer favNoteId) throws Exception{
		 
		 notesService.unFavouriteNotes(favNoteId);
		 return CommonUtil.createBuildResponseMessage("Remove Favourite Note", HttpStatus.OK);
	 }
	 
//	 @GetMapping("/fav-note")
//	 @PreAuthorize("hasRole('USER')")
	@Override
	 public ResponseEntity<?> getUserFavouriteNote() throws Exception{
		 
		 List<FavouriteNoteDto> favouriteNotes = notesService.getUserFavouriteNotes();
		 if(CollectionUtils.isEmpty(favouriteNotes)) {
			 return ResponseEntity.noContent().build();
		 }
		 return CommonUtil.createBuildResponse(favouriteNotes, HttpStatus.OK);
	 }
	 
//	 @GetMapping("/copy/{id}")
//	 @PreAuthorize("hasRole('USER')")
	@Override
	 public ResponseEntity<?> copyNotes( Integer id) throws Exception{
		 
		 Boolean copyNotes = notesService.copyNotes(id);
		 if(copyNotes) {
			return  CommonUtil.createBuildResponseMessage("Copy Successfully", HttpStatus.CREATED);
		 }
		 return CommonUtil.createBuildResponseMessage("Copy failed ! Try Again ", HttpStatus.INTERNAL_SERVER_ERROR);
	 }
}
