package com.zan.tasks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zan.tasks.model.Board;
import com.zan.tasks.model.Task;

public interface TaskDAO  extends JpaRepository<Task, Long>{
	List<Task> findByBoard(Board board);
}