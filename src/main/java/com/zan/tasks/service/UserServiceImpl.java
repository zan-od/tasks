package com.zan.tasks.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.zan.tasks.dao.RoleDAO;
import com.zan.tasks.dao.UserDAO;
import com.zan.tasks.model.Board;
import com.zan.tasks.model.Role;
import com.zan.tasks.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		roles.add(roleDAO.getOne(1));
		user.setRoles(roles);
		
		userDAO.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userDAO.findByUsername(username);
	}

	@Override
	public void setCurrentBoard(User user, Board board) {
		user.setCurrentBoard(board);
		userDAO.save(user);
	}
	
	@Override
	public Set<Board> getBoards(User user) {
		User loadedUser = userDAO.getOne((long) user.getId());
		return loadedUser.getBoards();
	}

	@Override
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return findByUsername(auth.getName());
	}

	@Override
	public void addBoard(User user, Board board) {
		Set<Board> boards = new HashSet<>();
		boards.add(board);
		user.setBoards(boards);
		userDAO.save(user);
	}
}
