package com.zan.tasks.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zan.tasks.model.Board;

public interface BoardDAO extends JpaRepository<Board, Long> {

}
