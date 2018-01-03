package com.zan.tasks.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zan.tasks.dao.TaskDAO;
import com.zan.tasks.dao.TimeIntervalDAO;
import com.zan.tasks.model.Board;
import com.zan.tasks.model.Task;
import com.zan.tasks.model.TimeInterval;
import com.zan.tasks.model.User;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskDAO taskDAO;
	
	@Autowired
	TimeIntervalDAO timeIntervalDAO;
	
	@Override
	public void saveTask(Task task) {
		taskDAO.save(task);
	}

	@Override
	public Task getTask(Long id) {
		return taskDAO.getOne(id);
	}
	
	@Override
	public List<Task> listTasks() {
		return taskDAO.findAll();
	}

	@Override
	public List<Task> listBoardTasks(Board board) {
		return taskDAO.findByBoard(board);
	}
	
	@Override
	public void deleteTask(Long id) {
		taskDAO.delete(id);
	}

	@Override
	public void startTask(Task task, User user) {
		if (isTaskStarted(task, user)){
			return;
		}
		
		TimeInterval newInterval = new TimeInterval();
		newInterval.setTask(task);
		newInterval.setPerformer(user);
		newInterval.setStartTime(new Date());
		
		List<TimeInterval> intervals = task.getTimeIntervals();
		intervals.add(newInterval);
		task.setTimeIntervals(intervals);
		
		timeIntervalDAO.save(newInterval);
		taskDAO.save(task);
	}

	@Override
	public void stopTask(Task task, User user) {
		TimeInterval interval = getStartedByUserInterval(task, user);
		if (interval == null){
			return;
		}
		
		interval.setStopTime(new Date());
		timeIntervalDAO.save(interval);
	}

	@Override
	public boolean isTaskStarted(Task task, User user) {
		return getStartedByUserInterval(task, user) != null;
	}

	@Override
	public List<TimeInterval> getIntervalsByUser(Task task, User user) {
		List<TimeInterval> intervals = new ArrayList<>();
		
		for (TimeInterval timeInterval : task.getTimeIntervals()) {
			if (timeInterval.getPerformer() == user) {
				intervals.add(timeInterval);
			}
		}
		
		return intervals;
	}

	@Override
	public TimeInterval getStartedByUserInterval(Task task, User user) {
		for (TimeInterval timeInterval : getIntervalsByUser(task, user)) {
			if (timeInterval.isStarted()) {
				return timeInterval;
			}
		}
		
		return null;
	}

	@Override
	public Integer getTaskDuration(Task task, User user) {
		int taskDuration = 0;
		int intervalDuration = 0;
		Date currentTime = new Date();
		for (TimeInterval timeInterval : getIntervalsByUser(task, user)) {
			if (timeInterval.isStarted()) {
				intervalDuration = TimeInterval.getIntervalDuration(timeInterval.getStartTime(), currentTime);
			} else {
				intervalDuration = timeInterval.getDuration();
			}
			
			taskDuration += intervalDuration;
		}
		
		return taskDuration;
	}
}
