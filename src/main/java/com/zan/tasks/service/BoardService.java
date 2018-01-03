package com.zan.tasks.service;

import java.util.List;

import com.zan.tasks.model.Board;
import com.zan.tasks.model.User;

public interface BoardService {
	
	public void addBoard(Board board, User user);
	
	public Board getBoard(Long id);
	
	public List<Board> getBoards(User user);

}
