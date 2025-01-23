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
import static com.coder.util.Constants.ROLE_ADMIN;
import static com.coder.util.Constants.ROLE_USER;
import static com.coder.util.Constants.ROLE_ADMIN_USER;
import static com.coder.util.Constants.DEFAULT_PAGE_NO;
import static com.coder.util.Constants.DEFAULT_PAGE_SIZE;;


@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

	@PostMapping("/save")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception;
	
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllNotes();
	
	// Search Notes 
		@GetMapping("/search")
		@PreAuthorize(ROLE_USER)
		public ResponseEntity<?> searchNotes(@RequestParam(name="key",defaultValue = "") String key,
												@RequestParam(name="pageNo" ,defaultValue = DEFAULT_PAGE_NO)Integer pageNo,
				 								@RequestParam(name="pageSize",defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
		
		// download Notes
		 @GetMapping("/download/{id}")
		 @PreAuthorize(ROLE_ADMIN_USER)
		 public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
		 
		// Pagination Notes
		 @GetMapping("/user-notes")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo" ,defaultValue = DEFAULT_PAGE_NO)Integer pageNo,
				 									@RequestParam(name="pageSize",defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
		 
		// delete Related API
		 @GetMapping("/delete/{id}")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
		 
		 @GetMapping("/restore/{id}")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;
		 
		 @GetMapping("/recycle-bin")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> getUserRecycleBinNotes();
		 
		 @DeleteMapping("/delete/{id}")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
		 
		 @DeleteMapping("/delete")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> emptyRecycleBin();
		 
		// favourite Note Related API
		 
		 @GetMapping("/fav/{noteId}")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId ) throws Exception;
		 
		 @DeleteMapping("/unfav/{favNoteId}")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNoteId) throws Exception;
		 
		 @GetMapping("/fav-note")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> getUserFavouriteNote() throws Exception;
		 
		 @GetMapping("/copy/{id}")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;
}
