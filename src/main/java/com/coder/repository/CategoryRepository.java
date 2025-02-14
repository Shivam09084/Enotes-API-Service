package com.coder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coder.entity.Category;

public interface CategoryRepository  extends JpaRepository<Category, Integer>{

	List<Category> findByIsActiveTrueAndIsDeletedFalse();

	Optional<Category> findByIdAndIsDeletedFalse(Integer id);

	List<Category> findByIsDeletedFalse();

	Category findByName(String name);

	Boolean existsByName(String trim);

}
