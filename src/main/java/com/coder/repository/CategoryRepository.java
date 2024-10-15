package com.coder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coder.entity.Category;

public interface CategoryRepository  extends JpaRepository<Category, Integer>{

}
