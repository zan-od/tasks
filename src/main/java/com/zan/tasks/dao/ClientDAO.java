package com.zan.tasks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zan.tasks.model.Board;
import com.zan.tasks.model.Client;

public interface ClientDAO  extends JpaRepository<Client, Long>{
	List<Client> findByBoardAndNameContainingIgnoreCase(Board board, String name);
	List<Client> findByBoard(Board board);
}
