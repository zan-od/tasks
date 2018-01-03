package com.zan.tasks.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zan.tasks.model.Role;

public interface RoleDAO extends JpaRepository<Role, Integer>{

}
