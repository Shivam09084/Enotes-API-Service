package com.coder.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.coder.dto.NotesDto;
import com.coder.dto.NotesDto.CategoryDto;
import com.coder.dto.NotesDto.FilesDto;
import com.coder.dto.NotesResponse;
import com.coder.entity.FileDetails;
import com.coder.entity.Notes;
import com.coder.exception.ResourceNotFoundException;
import com.coder.repository.CategoryRepository;
import com.coder.repository.FileRepository;
import com.coder.repository.NotesRepository;
import com.coder.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService{

	@Autowired
	private NotesRepository notesRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Autowired
	private FileRepository fileRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception {
		
		
		ObjectMapper ob = new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class);
		
		notesDto.setIsDeleted(false);
		notesDto.setDeletedOn(null);
		// updating Notes and checking id
		if(!ObjectUtils.isEmpty(notesDto.getId())) {
			updateNotes(notesDto,file);
		}
		
		// check Category Validation id
		checkCategoryExist(notesDto.getCategory());
		
		Notes notesmap = mapper.map(notesDto, Notes.class);
		
		FileDetails fileDtls = saveFileDetails(file);
		
		if(!ObjectUtils.isEmpty(fileDtls)) {
			notesmap.setFileDetails(fileDtls);
		}else {
			if(ObjectUtils.isEmpty(notesDto.getId())) {
				notesmap.setFileDetails(null);
			}
		}
		
		Notes  saveNotes = notesRepo.save(notesmap);
		if(!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}
		return false;
	}

	private void updateNotes(NotesDto notesDto, MultipartFile file) throws Exception {
		
		Notes existNotes = notesRepo.findById(notesDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Invalid  Notes id"));
		
		// user not
		if(ObjectUtils.isEmpty(file)) {
			notesDto.setFileDetails(mapper.map(existNotes.getFileDetails(), FilesDto.class));
		}
		
	}

	private FileDetails saveFileDetails(MultipartFile file) throws Exception {
		
		if(!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
			
			String originalFilename = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFilename);
			
			List<String> extensionAllow = Arrays.asList("jpg","png","pdf");
			if(!extensionAllow.contains(extension)) {
				throw new IllegalArgumentException("Invalid file format ! upload like .pdf, .png, .jpg");
			}
			
			String rndString = UUID.randomUUID().toString(); // random name create karega
			String uploadfileName = rndString + "." + extension; // sdfsafbhkljsf.pdf
			
			File saveFile = new File(uploadPath);  // ye Folder Create karega agar nhi hai
			if(!saveFile.exists()) {
				saveFile.mkdir();
			}
			
			// path : enotesapiservice/notes/java.pdf
			String storePath = uploadPath.concat(uploadfileName);  //ye path store karega
			
			// upload File 
			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			
			if(upload != 0) {
				
				FileDetails fileDtls = new FileDetails();
				fileDtls.setOriginalFileName(originalFilename);
				fileDtls.setDisplayFileName(getDisplayName(originalFilename));
				fileDtls.setUploadFileName(uploadfileName);
				fileDtls.setFileSize(file.getSize());
				fileDtls.setPath(storePath);
				FileDetails saveFileDtls = fileRepo.save(fileDtls);
				return saveFileDtls;
			}
		}
		return null;
	}

	private String getDisplayName(String originalFilename) {
		// java_programming_tutorials.pdf
		// java_prog.pdf  - func tion sub string store karega 
		
		String extension = FilenameUtils.getExtension(originalFilename);
		String fileName = FilenameUtils.removeExtension(originalFilename);
		
		if(fileName.length()>8) {
			 fileName = fileName.substring(0, 7);
		}
		fileName = fileName+"."+extension;
		return fileName;
	}
	private void checkCategoryExist(CategoryDto category) throws Exception {
		
		categoryRepo.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("Category Id Is Invalid"));
	}

	@Override
	public List<NotesDto> getAllNotes() {
		
		return notesRepo.findAll().stream().map(note -> mapper.map(note, NotesDto.class)).toList();
	}
	
// Download File Logic 
	
	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {
		
		InputStream io = new FileInputStream(fileDetails.getPath());
		return StreamUtils.copyToByteArray(io);
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		
		FileDetails fileDetails = fileRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("File is not available"));
		return fileDetails;
	}
// Page Pagination Logic
	
	@Override
	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Notes> pageNotes = notesRepo.findByCreatedByAndIsDeletedFalse(userId, pageable);
		
		List<NotesDto> notesDto = pageNotes.get().map(n -> mapper.map(n, NotesDto.class)).toList();
		NotesResponse notes = NotesResponse.builder().notes(notesDto)
											.pageNo(pageNotes.getNumber())
											.pageSize(pageNotes.getSize())
											.totalElements(pageNotes.getTotalElements())
											.totalPages(pageNotes.getTotalPages())
											.isFirst(pageNotes.isFirst())
											.isLast(pageNotes.isLast())
											.build();
		return notes;
	}
	
	// Delete Notes Logic 
	public void softDeleteNotes(Integer userId) throws Exception {
		
		Notes notes = notesRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Notes id !!! Invalid not found"));
		notes.setIsDeleted(true);
		notes.setDeletedOn(LocalDateTime.now());
		notesRepo.save(notes);
	}

	public void restoreNotes(Integer userId) throws Exception {
			
			Notes notes = notesRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Notes id !!! Invalid not found"));
			notes.setIsDeleted(false);
			notes.setDeletedOn(null);
			notesRepo.save(notes);
		}

	@Override
	public List<NotesDto> getUserRecycleBinNotes(Integer userId) {
		List<Notes> recycleNotes = notesRepo.findByCreatedByAndIsDeletedTrue(userId);
		List<NotesDto> list = recycleNotes.stream().map(note -> mapper.map(note, NotesDto.class)).toList();
		
		return list;
	}

	// Scheduling and hard Note delete Concepts
	
	@Override
	public void hardDeleteNotes(Integer id) throws Exception {
		
		Notes notes = notesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(" Notes Not Found "));
		if(notes.getIsDeleted()) {
			notesRepo.delete(notes);
		}else {
			throw new IllegalArgumentException("Sorry U can't hard delete directly");
		}
		
	}

	@Override
	public void emptyRecycleBin(Integer userId) {
		List<Notes> recycleNotes = notesRepo.findByCreatedByAndIsDeletedTrue(userId);
		if(!CollectionUtils.isEmpty(recycleNotes)) {
			notesRepo.deleteAll(recycleNotes);
		}
		
	}
	
	
}
