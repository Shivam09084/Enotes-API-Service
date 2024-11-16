package com.coder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteNoteDto {
	
	private Integer id;
	private NotesDto note;
	private Integer userId;

}
