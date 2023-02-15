package com.hyeji.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyeji.blog.model.Board;


public interface BoardRepository extends JpaRepository<Board, Integer>{
	

	
}
