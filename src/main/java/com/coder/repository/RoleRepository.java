package com.coder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coder.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	
}
