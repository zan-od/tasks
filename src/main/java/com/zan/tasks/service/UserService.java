package com.zan.tasks.service;

import java.util.Set;

import com.zan.tasks.model.Board;
import com.zan.tasks.model.User;

public interface UserService {
	void save(User user);
	
	User findByUsername(String username);
	
	void addBoard(User user, Board board);
	
	void setCurrentBoard(User user, Board board);
	
	Set<Board> getBoards(User user);
	
	User getCurrentUser();
}
