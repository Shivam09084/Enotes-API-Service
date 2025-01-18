package com.coder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coder.entity.FileDetails;

public interface FileRepository extends JpaRepository<FileDetails, Integer>{

}
