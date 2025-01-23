package com.coder.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

	@PostMapping("/save")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception;
	
	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllNotes();
	
	// Search Notes 
		@GetMapping("/search")
		@PreAuthorize("hasRole('USER')")
		public ResponseEntity<?> searchNotes(@RequestParam(name="key",defaultValue = "") String key,
												@RequestParam(name="pageNo" ,defaultValue = "0")Integer pageNo,
				 								@RequestParam(name="pageSize",defaultValue = "5") Integer pageSize);
		
		// download Notes
		 @GetMapping("/download/{id}")
		 @PreAuthorize("hasAnyRole('ADMIN','USER')")
		 public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
		 
		// Pagination Notes
		 @GetMapping("/user-notes")
		 @PreAuthorize("hasRole('USER')")
		 public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo" ,defaultValue = "0")Integer pageNo,
				 									@RequestParam(name="pageSize",defaultValue = "5") Integer pageSize);
		 
		// delete Related API
		 @GetMapping("/delete/{id}")
		 @PreAuthorize("hasRole('USER')")
		 public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
		 
		 @GetMapping("/restore/{id}")
		 @PreAuthorize("hasRole('USER')")
		 public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;
		 
		 @GetMapping("/recycle-bin")
		 @PreAuthorize("hasRole('USER')")
		 public ResponseEntity<?> getUserRecycleBinNotes();
		 
		 @DeleteMapping("/delete/{id}")
		 @PreAuthorize("hasRole('USER')")
		 public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
		 
		 @DeleteMapping("/delete")
		 @PreAuthorize("hasRole('USER')")
		 public ResponseEntity<?> emptyRecycleBin();
		 
		// favourite Note Related API
		 
		 @GetMapping("/fav/{noteId}")
		 @PreAuthorize("hasRole('USER')")
		 public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId ) throws Exception;
		 
		 @DeleteMapping("/unfav/{favNoteId}")
		 @PreAuthorize("hasRole('USER')")
		 public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNoteId) throws Exception;
		 
		 @GetMapping("/fav-note")
		 @PreAuthorize("hasRole('USER')")
		 public ResponseEntity<?> getUserFavouriteNote() throws Exception;
		 
		 @GetMapping("/copy/{id}")
		 @PreAuthorize("hasRole('USER')")
		 public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;
}
