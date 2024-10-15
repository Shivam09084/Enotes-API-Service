package com.coder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coder.entity.Category;

public interface CategoryRepository  extends JpaRepository<Category, Integer>{

	List<Category> findByIsActiveTrueAndIsDeletedFalse();

}
