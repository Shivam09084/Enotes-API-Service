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

import com.coder.dto.NotesRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import static com.coder.util.Constants.ROLE_ADMIN;
import static com.coder.util.Constants.ROLE_USER;
import static com.coder.util.Constants.ROLE_ADMIN_USER;
import static com.coder.util.Constants.DEFAULT_PAGE_NO;
import static com.coder.util.Constants.DEFAULT_PAGE_SIZE;;

@Tag(name = "Notes" ,description = "All The Notes Operation API's")
@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {

	@Operation(summary = "save notes",tags= {"Notes","User"}, description = "User save Notes")
	@PostMapping("/save")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveNotes(@RequestParam @Parameter(description = "JSON String Notes" , required = true, 
									content = @Content(schema = @Schema(implementation = NotesRequest.class))) String notes,
									@RequestParam(required = false) MultipartFile file) throws Exception;
	
	
	@Operation(summary = "Get All Notes",tags = {"Notes"}, description = "Admin get All Notes")
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllNotes();
	
	// Search Notes 
		@Operation(summary = "Search notes",tags= {"Notes","User"}, description = "User Search Notes")
		@GetMapping("/search")
		@PreAuthorize(ROLE_USER)
		public ResponseEntity<?> searchNotes(@RequestParam(name="key",defaultValue = "") String key,
												@RequestParam(name="pageNo" ,defaultValue = DEFAULT_PAGE_NO)Integer pageNo,
				 								@RequestParam(name="pageSize",defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
		
		// download Notes
		 @Operation(summary = "Downlod notes By id",tags= {"Notes","User"}, description = "User , Admin Download Notes By id")
		 @GetMapping("/download/{id}")
		 @PreAuthorize(ROLE_ADMIN_USER)
		 public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
		 
		 
		// Pagination Notes
		 @Operation(summary = "Get All notes User",tags= {"Notes","User"}, description = "User Get ALL Notes")
		 @GetMapping("/user-notes")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo" ,defaultValue = DEFAULT_PAGE_NO)Integer pageNo,
				 									@RequestParam(name="pageSize",defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
		
		 
		// delete Related API
		 @Operation(summary = "Delete notes",tags= {"Notes","User"}, description = "User Delete Notes by id")
		 @GetMapping("/delete/{id}")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
		 
		 
		 @Operation(summary = "Restore Delete notes",tags= {"Notes","User"}, description = "User Restore Notes from Recycle Bin")
		 @GetMapping("/restore/{id}")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;
		 
		 
		 @Operation(summary = "Get notes RecycleBin",tags= {"Notes","User"}, description = "User Get Notes from RecycleBin")
		 @GetMapping("/recycle-bin")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> getUserRecycleBinNotes();
		 
		 
		 @Operation(summary = "Hard Delete notes",tags= {"Notes","User"}, description = "Hard Delete Notes")
		 @DeleteMapping("/delete/{id}")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
		 
		 
		 @Operation(summary = "Empty User Recycle Bin",tags= {"Notes","User"}, description = "Empty User Recycle Bin")
		 @DeleteMapping("/delete")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> emptyRecycleBin();
		 
		// favourite Note Related API
		 
		 @Operation(summary = "User Favourite notes",tags= {"Notes","User"}, description = "User Favourite Notes")
		 @GetMapping("/fav/{noteId}")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId ) throws Exception;
		 
		 
		 @Operation(summary = "UnFavourite notes",tags= {"Notes","User"}, description = "User UnFavourite Notes")
		 @DeleteMapping("/unfav/{favNoteId}")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> unFavouriteNote(@PathVariable Integer favNoteId) throws Exception;
		 
		 
		 @Operation(summary = "Get User Favourite notes",tags= {"Notes","User"}, description = "Get User Favourite Notes")
		 @GetMapping("/fav-note")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> getUserFavouriteNote() throws Exception;
		 
		 
		 @Operation(summary = "Copy notes",tags= {"Notes","User"}, description = "Copy Notes By Id")
		 @GetMapping("/copy/{id}")
		 @PreAuthorize(ROLE_USER)
		 public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;
}
