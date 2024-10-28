package com.coder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coder.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer>{

}
