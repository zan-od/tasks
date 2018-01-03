package com.zan.tasks.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zan.tasks.dao.BoardDAO;
import com.zan.tasks.model.Board;
import com.zan.tasks.model.User;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO boardDAO;

	@Autowired
	UserService userService;
	
	@Override
	public void addBoard(Board board, User user) {
		Set<User> users = new HashSet<>();
		users.add(user);
		boardDAO.save(board);
		
		userService.setCurrentBoard(user, board);
	}

	@Override
	public Board getBoard(Long id) {
		return boardDAO.getOne(id);
	}

	@Override
	public List<Board> getBoards(User user) {
		List<Board> list = new ArrayList<Board>(user.getBoards());     
		Collections.sort(list, new Comparator<Board>() {
        public int compare(Board board1, Board board2) {
                return board1.getName().compareTo(board2.getName());
        }});
		
		return list;
	}
	
}
