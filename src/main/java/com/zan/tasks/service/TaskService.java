package com.zan.tasks.service;

import java.util.List;

import com.zan.tasks.model.Board;
import com.zan.tasks.model.Task;
import com.zan.tasks.model.TaskStatus;
import com.zan.tasks.model.TimeInterval;
import com.zan.tasks.model.User;

public interface TaskService {
	public void saveTask(Task task);

	public List<Task> listTasks();
	
	public List<Task> listBoardTasks(Board board);
	
	public List<Task> listBoardTasks(Board board, TaskStatus status);

	public Task getTask(Long id);
	
	public void deleteTask(Long id);
	
	public void startTask(Task task, User user);
	
	public void stopTask(Task task, User user);
	
	public boolean isTaskStarted(Task task, User user);
	
	public List<TimeInterval> getIntervalsByUser(Task task, User user);
	
	public TimeInterval getStartedByUserInterval(Task task, User user);
	
	public Integer getTaskDuration(Task task, User user);
	
	public List<TaskStatus> getAllTaskStatuses();
	
	public List<TaskStatus> getTaskStatusesToChange(TaskStatus status);
	
	public void SetTasksStatus(List<Task> tasks, TaskStatus status);
}
