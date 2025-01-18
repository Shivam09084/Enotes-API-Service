package com.coder.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coder.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer>{
	
	Page<Notes> findByCreatedBy (Integer userId, Pageable pageable);

	Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId, Pageable pageable);
	
	List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);

	List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOffDate);
	
	@Query("select n from Notes n where (Lower(n.title) like lower(concat('%',:keyword,'%')) "
			+ "or lower(n.description) like lower(concat('%',:keyword,'%')) "
			+ "or lower(n.category.name) like lower(concat('%',:keyword,'%'))) "
			+ "and n.isDeleted=false "
			+ "and n.createdBy=:userId")
	Page<Notes> searchNotes(@Param("keyword") String keyword, @Param("userId")Integer userId, Pageable pageable);

}
