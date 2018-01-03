package com.zan.tasks.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zan.tasks.model.User;

public interface UserDAO extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
