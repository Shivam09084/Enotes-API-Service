package com.coder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coder.entity.FavouriteNote;

public interface FavouriteNoteRepository extends JpaRepository<FavouriteNote, Integer> {
	
	List<FavouriteNote> findByUserId(Integer userId);
	boolean existsByNoteIdAndUserId(Integer noteId , Integer userId);
}
